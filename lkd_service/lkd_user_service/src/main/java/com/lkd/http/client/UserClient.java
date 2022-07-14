package com.lkd.http.client;

import com.lkd.entity.UserEntity;
import com.lkd.feign.UserService;
import com.lkd.vo.PartnerVO;
import com.lkd.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserClient implements UserService {
    @Autowired
    private com.lkd.service.UserService userService;

    @Override
    public UserVO getUser(int id) {
        return null;
    }

    @Override
    public List<UserVO> getRepairers(String regionId) {
        return null;
    }

    @Override
    public List<UserVO> getOperators(String regionId) {
        return null;
    }

    @Override
    public List<UserVO> getUserList() {
        List<UserEntity> list = userService.list();

        return list.stream().map(
                user -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(user, userVO);
                    userVO.setUserId(user.getId());

                    return userVO;
                }
        ).collect(Collectors.toList());

    }

    @Override
    public Integer getOperatorCount() {
        return null;
    }

    @Override
    public Integer getRepairerCount() {
        return null;
    }

    /**
     * 根据售货机获取运营人员列表
     */
    @GetMapping("/operatorList/{innerCode}")
    public ArrayList<UserVO> getOperatorListByInnerCode(@PathVariable String innerCode) {
        ArrayList<UserVO> userlist = userService.getUser(innerCode);
        return userlist;
    }

    /**
     * 根据售货机获取维修人员列表
     */
    @GetMapping("/repairerList/{innerCode}")
    public ArrayList<UserVO> getRepairerListByInnerCode(@PathVariable String innerCode) {
        ArrayList<UserVO> userlist = userService.getrepairerList(innerCode);
        return userlist;
    }

    @Override
    public PartnerVO getPartner(Integer id) {
        return null;
    }

    @Override
    public Integer getCountByRegion(Long regionId, Boolean isRepair) {
        return null;
    }

    @Override
    public String getPartnerName(Integer id) {
        return null;
    }


}
