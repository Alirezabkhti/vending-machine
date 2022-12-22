package com.vub.assessment.vendingmachine.domain.user;

public enum UserRole {

    ROLE_BUYER(0,"ROLE_BUYER"),
    ROLE_SELLER(1,"ROLE_SELLER"),
	ROLE_ADMIN(2,"ROLE_ADMIN");

    private final Integer index;
    private final String title;

    UserRole(Integer index, String title) {
        this.index = index;
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public static UserRole getByUserRoleTitle(String userRoleTitle){
        for(UserRole userRole : UserRole.values()){
            if(userRole.getTitle().equals(userRoleTitle))
                return userRole;
        }
		return ROLE_ADMIN;
    }
}
