package org.jeecg.modules.system.controller;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.*;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.system.entity.SysThirdAccount;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.ThirdLoginModel;
import org.jeecg.modules.system.service.ISysThirdAccountService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author scott
 * @since 2018-12-17
 */
@Controller
@RequestMapping("/sys/thirdLogin")
@Slf4j
public class ThirdLoginController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysThirdAccountService sysThirdAccountService;

    @Autowired
    private BaseCommonService baseCommonService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthRequestFactory factory;

    @RequestMapping("/render/{source}")
    public void render(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        log.info("?????????????????????render???" + source);
        AuthRequest authRequest = factory.get(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("??????????????????????????????" + authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    @RequestMapping("/{source}/callback")
    public String loginThird(@PathVariable("source") String source, AuthCallback callback, ModelMap modelMap) {
        log.info("?????????????????????callback???" + source + " params???" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = factory.get(source);
        AuthResponse response = authRequest.login(callback);
        log.info(JSONObject.toJSONString(response));
        Result<JSONObject> result = new Result<JSONObject>();
        if (response.getCode() == 2000) {

            JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(response.getData()));
            String username = data.getString("username");
            String avatar = data.getString("avatar");
            String uuid = data.getString("uuid");
            //???????????????????????????????????????
            ThirdLoginModel tlm = new ThirdLoginModel(source, uuid, username, avatar);
            //????????????????????????
            //update-begin-author:wangshuai date:20201118 for:?????????????????????????????????
            LambdaQueryWrapper<SysThirdAccount> query = new LambdaQueryWrapper<SysThirdAccount>();
            query.eq(SysThirdAccount::getThirdUserUuid, uuid);
            query.eq(SysThirdAccount::getThirdType, source);
            List<SysThirdAccount> thridList = sysThirdAccountService.list(query);
            SysThirdAccount user = null;
            if (thridList == null || thridList.size() == 0) {
                //???????????????????????????
                user = saveThirdUser(tlm);
            } else {
                //????????? ?????????????????? ???????????????
                user = thridList.get(0);
            }
            // ??????token
            //update-begin-author:wangshuai date:20201118 for:??????????????????????????????????????????id???????????????????????????
            if (oConvertUtils.isNotEmpty(user.getSysUserId())) {
                String sysUserId = user.getSysUserId();
                SysUser sysUser = sysUserService.getById(sysUserId);
                String token = saveToken(sysUser);
                modelMap.addAttribute("token", token);
            } else {
                modelMap.addAttribute("token", "???????????????," + "" + uuid);
            }
            //update-end-author:wangshuai date:20201118 for:??????????????????????????????????????????id???????????????????????????
            //update-begin--Author:wangshuai  Date:20200729 for????????????????????????????????????????????????????????? issues#1441--------------------
        } else {
            modelMap.addAttribute("token", "????????????");
        }
        //update-end--Author:wangshuai  Date:20200729 for????????????????????????????????????????????????????????? issues#1441--------------------
        result.setSuccess(false);
        result.setMessage("?????????????????????,??????????????????");
        return "thirdLogin";
    }

    /**
     * ???????????????
     *
     * @param model
     * @return
     */
    @PostMapping("/user/create")
    @ResponseBody
    public Result<String> thirdUserCreate(@RequestBody ThirdLoginModel model) {
        log.info("?????????????????????????????????");
        Result<String> res = new Result<>();
        Object operateCode = redisUtil.get(CommonConstant.THIRD_LOGIN_CODE);
        if (operateCode == null || !operateCode.toString().equals(model.getOperateCode())) {
            res.setSuccess(false);
            res.setMessage("????????????");
            return res;
        }
        //???????????????
        //update-begin-author:wangshuai date:20201118 for:???????????????????????????????????????user_id???????????????????????????token
        SysThirdAccount user = saveThirdUser(model);
        if (oConvertUtils.isNotEmpty(user.getSysUserId())) {
            String sysUserId = user.getSysUserId();
            SysUser sysUser = sysUserService.getById(sysUserId);
            // ??????token
            String token = saveToken(sysUser);
            //update-end-author:wangshuai date:20201118 for:???????????????????????????????????????user_id???????????????????????????token
            res.setResult(token);
            res.setSuccess(true);
        }
        return res;
    }

    /**
     * ???????????? ?????????????????? ?????????????????????
     *
     * @param json
     * @return
     */
    @PostMapping("/user/checkPassword")
    @ResponseBody
    public Result<String> checkPassword(@RequestBody JSONObject json) {
        Result<String> result = new Result<>();
        Object operateCode = redisUtil.get(CommonConstant.THIRD_LOGIN_CODE);
        if (operateCode == null || !operateCode.toString().equals(json.getString("operateCode"))) {
            result.setSuccess(false);
            result.setMessage("????????????");
            return result;
        }
        String username = json.getString("uuid");
        SysUser user = this.sysUserService.getUserByName(username);
        if (user == null) {
            result.setMessage("???????????????");
            result.setSuccess(false);
            return result;
        }
        String password = json.getString("password");
        String salt = user.getSalt();
        String passwordEncode = PasswordUtil.encrypt(user.getUsername(), password, salt);
        if (!passwordEncode.equals(user.getPassword())) {
            result.setMessage("???????????????");
            result.setSuccess(false);
            return result;
        }

        sysUserService.updateById(user);
        result.setSuccess(true);
        // ??????token
        String token = saveToken(user);
        result.setResult(token);
        return result;
    }

    /**
     * ???????????????
     *
     * @param tlm ?????????????????????
     */
    private SysThirdAccount saveThirdUser(ThirdLoginModel tlm) {
        SysThirdAccount user = new SysThirdAccount();
        user.setDelFlag(CommonConstant.DEL_FLAG_0);
        user.setStatus(1);
        user.setThirdType(tlm.getSource());
        user.setAvatar(tlm.getAvatar());
        user.setRealname(tlm.getUsername());
        user.setThirdUserUuid(tlm.getUuid());
        sysThirdAccountService.save(user);
        return user;
    }

    private String saveToken(SysUser user) {
        // ??????token
        String token = JwtUtil.sign(user.getUsername(), user.getPassword());
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        // ??????????????????
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
        return token;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getLoginUser/{token}/{thirdType}", method = RequestMethod.GET)
    @ResponseBody
    public Result<JSONObject> getThirdLoginUser(@PathVariable("token") String token, @PathVariable("thirdType") String thirdType) throws Exception {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = JwtUtil.getUsername(token);

        //1. ????????????????????????
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }
        //update-begin-author:wangshuai date:20201118 for:????????????????????????????????????????????????????????????
        LambdaQueryWrapper<SysThirdAccount> query = new LambdaQueryWrapper<>();
        query.eq(SysThirdAccount::getSysUserId, sysUser.getId());
        query.eq(SysThirdAccount::getThirdType, thirdType);
        SysThirdAccount account = sysThirdAccountService.getOne(query);
        if (oConvertUtils.isEmpty(sysUser.getRealname())) {
            sysUser.setRealname(account.getRealname());
        }
        if (oConvertUtils.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(account.getAvatar());
        }
        //update-end-author:wangshuai date:20201118 for:????????????????????????????????????????????????????????????
        JSONObject obj = new JSONObject();
        //??????????????????
        obj.put("userInfo", sysUser);
        //token ??????
        obj.put("token", token);
        result.setResult(obj);
        result.setSuccess(true);
        result.setCode(200);
        baseCommonService.addLog("?????????: " + username + ",????????????[???????????????]???", CommonConstant.LOG_TYPE_1, null);
        return result;
    }

    /**
     * ??????????????????????????????token
     *
     * @param jsonObject
     * @return
     */
    @ApiOperation("?????????????????????")
    @PostMapping("/bindingThirdPhone")
    @ResponseBody
    public Result<String> bindingThirdPhone(@RequestBody JSONObject jsonObject) {
        Result<String> result = new Result<String>();
        String phone = jsonObject.getString("mobile");
        String thirdUserUuid = jsonObject.getString("thirdUserUuid");
        //?????????????????????
        SysUser sysUser = sysUserService.getUserByPhone(phone);
        if (sysUser != null) {
            sysThirdAccountService.updateThirdUserId(sysUser, thirdUserUuid);
        } else {
            // ?????????????????????????????????
            String smscode = jsonObject.getString("captcha");
            Object code = redisUtil.get(phone);
            if (!smscode.equals(code)) {
                result.setMessage("?????????????????????");
                result.setSuccess(false);
                return result;
            }
            //????????????
            sysUser = sysThirdAccountService.createUser(phone, thirdUserUuid);
        }
        String token = saveToken(sysUser);
        result.setSuccess(true);
        result.setResult(token);
        return result;
    }
}