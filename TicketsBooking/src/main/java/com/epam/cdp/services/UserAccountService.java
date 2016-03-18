package com.epam.cdp.services;

import com.epam.cdp.model.UserAccount;

public interface UserAccountService {
    /**
     * Gets user account by its id.
     *
     * @return UserAccount.
     */
    UserAccount getUserAccountById(long userAccountId);

    /**
     * Gets user account by user id.
     *
     * @return UserAccount.
     */
    UserAccount getUserAccountByUserId(long userId);

    /**
     * Creates new user account. User account id should be auto-generated.
     *
     * @param userAccount UserAccount data.
     * @return Created UserAccount object.
     */
    UserAccount createUserAccount(UserAccount userAccount);

    /**
     * Updates user account using given data.
     *
     * @param userAccount User account data for update. Should have id set.
     * @return Updated UserAccount object.
     */
    UserAccount updateUserAccount(UserAccount userAccount);

    /**
     * Deletes user account by its id.
     *
     * @param userAccountId User account id.
     * @return Flag that shows whether user account has been deleted.
     */
    boolean deleteUserAccount(long userAccountId);
}
