package Entities;

public class HourlyWinRateEntity {
    public HourlyWinRateEntity() {}

    public PlayerEntity playerEntity;
    public double h00Percent;
    public double h01Percent;
    public double h02Percent;
    public double h03Percent;
    public double h04Percent;
    public double h05Percent;
    public double h06Percent;
    public double h07Percent;
    public double h08Percent;
    public double h09Percent;
    public double h10Percent;
    public double h11Percent;
    public double h12Percent;
    public double h13Percent;
    public double h14Percent;
    public double h15Percent;
    public double h16Percent;
    public double h17Percent;
    public double h18Percent;
    public double h19Percent;
    public double h20Percent;
    public double h21Percent;
    public double h22Percent;
    public double h23Percent;

    public int h00Wins;
    public int h01Wins;
    public int h02Wins;
    public int h03Wins;
    public int h04Wins;
    public int h05Wins;
    public int h06Wins;
    public int h07Wins;
    public int h08Wins;
    public int h09Wins;
    public int h10Wins;
    public int h11Wins;
    public int h12Wins;
    public int h13Wins;
    public int h14Wins;
    public int h15Wins;
    public int h16Wins;
    public int h17Wins;
    public int h18Wins;
    public int h19Wins;
    public int h20Wins;
    public int h21Wins;
    public int h22Wins;
    public int h23Wins;

    public int h00Games;
    public int h01Games;
    public int h02Games;
    public int h03Games;
    public int h04Games;
    public int h05Games;
    public int h06Games;
    public int h07Games;
    public int h08Games;
    public int h09Games;
    public int h10Games;
    public int h11Games;
    public int h12Games;
    public int h13Games;
    public int h14Games;
    public int h15Games;
    public int h16Games;
    public int h17Games;
    public int h18Games;
    public int h19Games;
    public int h20Games;
    public int h21Games;
    public int h22Games;
    public int h23Games;

    public void debugPrint(String summonerId) {
        System.out.println("Data for : " + summonerId);
        System.out.println("Hour  | Win % | Wins / Games");

        String[] hourLabels = {
                "1am", "2am", "3am", "4am", "5am",
                "6am", "7am", "8am", "9am", "10am", "11am",
                "Noon", "1pm", "2pm", "3pm", "4pm", "5pm",
                "6pm", "7pm", "8pm", "9pm", "10pm", "11pm", "Midnight"
        };

        for (int h = 1; h < 25; h++) {
            double percent;
            int wins, games;

            switch (h) {
                case 1 -> { percent = h01Percent; wins = h01Wins; games = h01Games; }
                case 2 -> { percent = h02Percent; wins = h02Wins; games = h02Games; }
                case 3 -> { percent = h03Percent; wins = h03Wins; games = h03Games; }
                case 4 -> { percent = h04Percent; wins = h04Wins; games = h04Games; }
                case 5 -> { percent = h05Percent; wins = h05Wins; games = h05Games; }
                case 6 -> { percent = h06Percent; wins = h06Wins; games = h06Games; }
                case 7 -> { percent = h07Percent; wins = h07Wins; games = h07Games; }
                case 8 -> { percent = h08Percent; wins = h08Wins; games = h08Games; }
                case 9 -> { percent = h09Percent; wins = h09Wins; games = h09Games; }
                case 10 -> { percent = h10Percent; wins = h10Wins; games = h10Games; }
                case 11 -> { percent = h11Percent; wins = h11Wins; games = h11Games; }
                case 12 -> { percent = h12Percent; wins = h12Wins; games = h12Games; }
                case 13 -> { percent = h13Percent; wins = h13Wins; games = h13Games; }
                case 14 -> { percent = h14Percent; wins = h14Wins; games = h14Games; }
                case 15 -> { percent = h15Percent; wins = h15Wins; games = h15Games; }
                case 16 -> { percent = h16Percent; wins = h16Wins; games = h16Games; }
                case 17 -> { percent = h17Percent; wins = h17Wins; games = h17Games; }
                case 18 -> { percent = h18Percent; wins = h18Wins; games = h18Games; }
                case 19 -> { percent = h19Percent; wins = h19Wins; games = h19Games; }
                case 20 -> { percent = h20Percent; wins = h20Wins; games = h20Games; }
                case 21 -> { percent = h21Percent; wins = h21Wins; games = h21Games; }
                case 22 -> { percent = h22Percent; wins = h22Wins; games = h22Games; }
                case 23 -> { percent = h23Percent; wins = h23Wins; games = h23Games; }
                case 24 -> { percent = h00Percent; wins = h00Wins; games = h00Games; }
                default -> { continue; }
            }

            System.out.printf("%-8s | %6.2f%% | %d / %d%n",
                    hourLabels[h - 1], percent, wins, games);
        }
    }
}
