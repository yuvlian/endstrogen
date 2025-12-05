package yuvlian.endstrogen;

import yuvlian.endstrogen.database.Database;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.SdkServer;

public class App {
    public static void main(String[] args) throws Exception {
        Account.DAO.createAccount("testUser", "hash123", "tokenABC");

        Account acc1 = Account.DAO.getByUsername("testUser");
        if (acc1 != null) {
            System.out.println("1------");
            System.out.println("uid=" + acc1.getUid());
            System.out.println("usn=" + acc1.getUsername());
            System.out.println("hsh=" + acc1.getPasswordHash());
            System.out.println("tkn=" + acc1.getToken());
        }

        Account.DAO.updatePwHashByUid(acc1.getUid(), "newHash456");
        Account.DAO.updateTokenByUid(acc1.getUid(), "newTokenXYZ");

        Account acc2 = Account.DAO.getByUid(acc1.getUid());
        if (acc2 != null) {
            System.out.println("2------");
            System.out.println("uid=" + acc2.getUid());
            System.out.println("usn=" + acc2.getUsername());
            System.out.println("hsh=" + acc2.getPasswordHash());
            System.out.println("tkn=" + acc2.getToken());
        }

        Account acc3 = Account.DAO.getByToken("newTokenXYZ");
        if (acc3 != null) {
            System.out.println("3------");
            System.out.println("uid=" + acc3.getUid());
            System.out.println("usn=" + acc3.getUsername());
            System.out.println("hsh=" + acc3.getPasswordHash());
            System.out.println("tkn=" + acc3.getToken());
        }

        Database.close();
        SdkServer.start();
    }
}
