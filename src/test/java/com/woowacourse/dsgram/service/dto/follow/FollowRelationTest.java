package com.woowacourse.dsgram.service.dto.follow;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.woowacourse.dsgram.service.dto.follow.FollowRelation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class FollowRelationTest {
    private static User guest = Mockito.mock(User.class);
    private static User feedOwner = Mockito.mock(User.class);

    @Test
    @DisplayName("MINE 관계 확인")
    void relation_mine() {
        Follow follow = new Follow(guest, feedOwner);
        Mockito.when(guest.isSameUser(any())).thenReturn(true);

        assertThat(getRelation(follow, guest, feedOwner)).isEqualByComparingTo(MINE);
    }

    @Test
    @DisplayName("FOLLOW 관계 확인")
    void relation_follow() {
        Mockito.when(guest.isSameUser(any())).thenReturn(false);

        assertThat(getRelation(null, guest, feedOwner)).isEqualByComparingTo(UNFOLLOW);
    }

    @Test
    @DisplayName("UNFOLLOW 관계 확인")
    void relation_unfollow() {
        Follow follow = new Follow(guest, feedOwner);
        Mockito.when(guest.isSameUser(any())).thenReturn(false);

        assertThat(getRelation(follow, guest, feedOwner)).isEqualByComparingTo(FOLLOW);
    }
}
