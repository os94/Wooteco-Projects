package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.FollowRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.FollowInfo;
import com.woowacourse.dsgram.service.dto.FollowRelation;
import com.woowacourse.dsgram.service.exception.InvalidFollowException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public FollowRelation isFollowed(User guest, User feedOwner) {
        Follow follow = getFollow(guest,feedOwner);
        return FollowRelation.getRelation(follow, guest, feedOwner);
    }

    public List<FollowInfo> findFollowers(User user) {
        List<FollowInfo> followers = followRepository.findAllByTo(user)
                .stream().map(Follow::getFrom)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followers;
    }

    public List<FollowInfo> findFollowings(User user) {
        List<FollowInfo> followings = followRepository.findAllByFrom(user)
                .stream().map(Follow::getTo)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followings;
    }

    public long getCountOfFollowers(User user) {
        return followRepository.countByTo(user);
    }

    public long getCountOfFollowings(User user) {
        return followRepository.countByFrom(user);
    }


    private Follow getFollow(User guest, User feedOwner) {
        return followRepository.findByFromAndTo(guest,feedOwner);
    }

    @Transactional
    public Follow save(User guest, User feedOwner) {
        if(followRepository.existsByFromAndTo(guest,feedOwner)) {
            throw new InvalidFollowException("이미 팔로우한 상태입니다.");
        }
        return followRepository.save(new Follow(guest,feedOwner));
    }

    @Transactional
    public void delete(User guest, User feedOwner) {
        if(!followRepository.existsByFromAndTo(guest,feedOwner)) {
            throw new InvalidFollowException("현재 팔로우 상태가 아닙니다.");
        }

        Follow follow = followRepository.findByFromAndTo(guest, feedOwner);
        followRepository.delete(follow);
    }
}
