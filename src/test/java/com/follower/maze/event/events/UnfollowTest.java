package com.follower.maze.event.events;

import com.follower.maze.users.DefaultUserHashMap;
import com.follower.maze.users.User;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnfollowTest {

    private final User newUserThree = mock(User.class);
    private final User newUserTwo = mock(User.class);
    private final Map<Integer, User> twoUsers = new DefaultUserHashMap(new HashMap<Integer, User>() {{
        put(2, newUserTwo);
        put(3, newUserThree);
    }});
    private final Unfollow unfollow = new Unfollow(1, "1|F|2|3", 2, 3);
    private final HashMap<Integer, User> noUsers = new HashMap<>();

    @Test
    public void testNotifyUsers() throws Exception {

        unfollow.notifyUsers(twoUsers);

        verify(newUserThree, new Times(0)).notifyFollowers(anyString());
    }

    @Test
    public void testFollowUserCorrectly() throws Exception {

        unfollow.notifyUsers(twoUsers);

        verify(newUserThree).removeFollower(newUserTwo);
    }

    @Test
    public void testNotifyWhenNoUserIsThereHasCorrectSize() throws Exception {

        unfollow.notifyUsers(noUsers);

        assertEquals(2, noUsers.size());
    }

    @Test
    public void testNotifyWhenNoUserIsThereHasCorrectKeys() throws Exception {

        unfollow.notifyUsers(noUsers);

        assertTrue(noUsers.containsKey(2));
        assertTrue(noUsers.containsKey(3));
    }

    @Test
    public void testNotifyWhenFromUserIsThere() throws Exception {
        noUsers.put(2, newUserTwo);

        unfollow.notifyUsers(noUsers);

        assertEquals(2, noUsers.size());
    }

    @Test
    public void testNotifyWhenToUserIsThere() throws Exception {
        noUsers.put(3, newUserThree);

        unfollow.notifyUsers(noUsers);

        assertEquals(2, noUsers.size());
    }
}