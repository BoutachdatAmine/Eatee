package be.ehb.progproject2.eatee.entity.request;

public class TwoFactorBody {
    private int userId;
    private String twoFactorKey;

    public int getUserId() {
        return userId;
    }
    public String getTwoFactorKey() {
        return twoFactorKey;
    }

    public void checkFields() throws Exception {
        if(userId < 0 || twoFactorKey == null)
            throw new Exception("The following fields are required: userId, twoFactorKey");
    }
}
