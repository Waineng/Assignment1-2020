import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A support class to complete main calculation of ShootingStreak
 */

public class ShootingStreakCouter {

    /**
     *
     * @param fileFolder name of fileFolder
     * @param gameNumStr Number of games to calculate(for example:1)
     * @param shotTypeStr shotType (1,2,3 or any)
     * @param players indicate which players would be shown in results
     * @return result of type HashMap
     * @throws IOException
     */

    public static HashMap<String, int[]> count(String fileFolder, String gameNumStr,
            String shotTypeStr, String[] players) throws IOException{
        File folder = new File(fileFolder);

        int count = 0;

        // initialize result map

        HashMap<String, int[]> map = new HashMap<>();
        for (String player : players) {
            map.put(player, new int[]{0, 0});
        }

        // read all files in fileFolder ,

        for (File file : folder.listFiles()) {
            int gameNum = gameNumStr.equals("all") ? folder.list().length : Integer.parseInt(gameNumStr);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            String curName = null, curShotIn = null, curShotType = null;
            int curCount = 0;

            // read each line into memory in file

            while ((line = br.readLine()) != null) {
                String[] datas = line.split(",");

                // read first line of first file

                if (curName == null) {
                    curName = datas[0];
                    curShotIn = datas[2];
                    curShotType = datas[3];
                    curCount = 1;
                    continue;
                }

                // data not interested in

                if (!shotTypeStr.equals("any") && !shotTypeStr.equals(datas[3]))
                    continue;
                if ((shotTypeStr.equals("any") && (!curName.equals(datas[0]) || !curShotIn.equals(datas[2])))
                        || (!shotTypeStr.equals("any") && (!curName.equals(datas[0]) || !curShotIn.equals(datas[2]) || !curShotType.equals(datas[3])))) {
                    updateMap(curShotIn, curName, curCount, map);
                    curName = datas[0];
                    curShotIn = datas[2];
                    curShotType = datas[3];
                    curCount = 1;
                }
                else
                    curCount++;
            }
            if (++count >= gameNum) {
                updateMap(curShotIn, curName, curCount, map);
                break;
            }
        }
        return map;
    }

    /**
     * update result map when reading new data
     * @param curShotIn current shotIn in statistics
     * @param curName current name in statistics
     * @param curCount current count in statistics
     * @param map result map
     */

    public static void updateMap(String curShotIn, String curName, int curCount, HashMap<String, int[]> map) {

        // make method

        if (curShotIn.equals("make")) {
            if (map.get(curName) != null) {
                map.get(curName)[1] = Math.max(map.get(curName)[1], curCount);
            }
        }

        // miss method

        else if (curShotIn.equals("miss")) {
            if (map.get(curName) != null) {
                map.get(curName)[0] = Math.max(map.get(curName)[0], curCount);
            }
        }
    }
}
