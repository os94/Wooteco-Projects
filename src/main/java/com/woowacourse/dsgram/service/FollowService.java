package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.FollowRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.follow.FollowRelation;
import com.woowacourse.dsgram.service.dto.user.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    FollowRelation isFollowed(User guest, User feedOwner) {
        Follow follow = getFollow(guest, feedOwner);
        return FollowRelation.getRelation(follow, guest, feedOwner);
    }

    @Transactional(readOnly = true)
    List<UserInfo> findFollowers(User user) {
        List<UserInfo> followers = followRepository.findAllByTo(user)
                .stream().map(Follow::getFrom)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followers;
    }

    @Transactional(readOnly = true)
    List<UserInfo> findFollowings(User user) {
        List<UserInfo> followings = followRepository.findAllByFrom(user)
                .stream().map(Follow::getTo)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followings;
    }

    long getCountOfFollowers(User user) {
        return followRepository.countByTo(user);
    }

    long getCountOfFollowings(User user) {
        return followRepository.countByFrom(user);
    }

    private Follow getFollow(User guest, User feedOwner) {
        return followRepository.findByFromAndTo(guest, feedOwner);
    }

    public Follow save(User guest, User feedOwner) {
        return followRepository.save(new Follow(guest, feedOwner));
    }

    public void delete(User guest, User feedOwner) {
        Follow follow = followRepository.findByFromAndTo(guest, feedOwner);
        followRepository.delete(follow);
    }

    boolean existRelation(User guest, User feedOwner) {
        return followRepository.existsByFromAndTo(guest, feedOwner);
    }

    public void follow(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);

        if (!existRelation(guest, feedOwner)) {
            save(guest, feedOwner);
            return;
        }
        delete(guest, feedOwner);
    }

    @Transactional(readOnly = true)
    public List<UserInfo> getFollowers(String nickName) {
        User user = userService.findByNickName(nickName);

        return findFollowers(user);
    }

    @Transactional(readOnly = true)
    public List<UserInfo> getFollowings(String nickName) {
        User user = userService.findByNickName(nickName);

        return findFollowings(user);
    }
}
