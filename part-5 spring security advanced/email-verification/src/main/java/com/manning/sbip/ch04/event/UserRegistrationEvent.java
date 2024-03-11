package com.manning.sbip.ch04.event;

import com.manning.sbip.ch04.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    private static final long serialVersionUID = -2685172945219633123L;

    private ApplicationUser applicationUser;

    public UserRegistrationEvent(ApplicationUser applicationUser) {
        super(applicationUser);
        this.applicationUser = applicationUser;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
}
