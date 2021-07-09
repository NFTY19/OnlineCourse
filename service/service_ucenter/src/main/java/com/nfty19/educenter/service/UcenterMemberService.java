package com.nfty19.educenter.service;

import com.nfty19.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nfty19.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    boolean register(RegisterVo registerVo);
}
