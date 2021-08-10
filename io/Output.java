package io;


public class Output {
    public static void showWelcomeMessage() {
        System.out.println("1. New user ");
        System.out.println("2. Existing user? Create new account ");
        System.out.println("3. Check Balance ");
        System.out.println("4. Show accounts ");
        System.out.println("Enter 0 to exit");
    }

    public static void showNewUser()  {
        System.out.println("Enter your name");
        System.out.println("Enter your mobile number");
        System.out.println("Enter address");
    }

    public static void showNewAccount() {
        System.out.println("Enter your user id");
        System.out.println("Enter branch");
    }

    public static void askUserId() {
        System.out.println("Enter user id");
    }
}
