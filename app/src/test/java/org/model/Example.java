package org.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sap.ass01.layered.domain.User;

public class Example {
    @Test
    public void test() {
        //example
        assert true;
    }
    @Test
    public void testUserId(){
        User user = new User("1");
        assertEquals("1", user.getId());
    }

}