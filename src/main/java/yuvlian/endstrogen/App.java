package yuvlian.endstrogen;

import yuvlian.endstrogen.Proto;

public class App {
    public static void main(String[] args) throws Exception {
        Proto.CsMissionClientTriggerDone msg =
            Proto.CsMissionClientTriggerDone.newBuilder()
                .setMissionId("mission_001")
                .setSceneName("TestScene")
                .setAreaId("Area_42")
                .build();

        System.out.println("Message:");
        System.out.println(msg);

        byte[] data = msg.toByteArray();
        System.out.println("\nSerialized bytes:");
        System.out.println(java.util.Arrays.toString(data));

        Proto.CsMissionClientTriggerDone parsed =
            Proto.CsMissionClientTriggerDone.parseFrom(data);

        System.out.println("\nParsed back:");
        System.out.println(parsed);
    }
}
