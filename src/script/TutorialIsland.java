package script;

import com.google.gson.JsonObject;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import sections.*;
import util.Sleep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@ScriptManifest(author = "Explv", name = "TutorialIsland", info = "Completes Tutorial Island", version=0, logo = "")
public final class TutorialIsland extends Script {
    public static final String VERSION = "v6.2";

    private final TutorialSection rsGuideSection = new RuneScapeGuideSection();
    private final TutorialSection survivalSection = new SurvivalSection();
    private final TutorialSection cookingSection = new CookingSection();
    private final TutorialSection questSection = new QuestSection();
    private final TutorialSection miningSection = new MiningSection();
    private final TutorialSection fightingSection = new FightingSection();
    private final TutorialSection bankSection = new BankSection();
    private final TutorialSection priestSection = new PriestSection();
    private final TutorialSection wizardSection = new WizardSection();


    @Override
    public void onStart() {
        rsGuideSection.exchangeContext(getBot());
        survivalSection.exchangeContext(getBot());
        cookingSection.exchangeContext(getBot());
        questSection.exchangeContext(getBot());
        miningSection.exchangeContext(getBot());
        fightingSection.exchangeContext(getBot());
        bankSection.exchangeContext(getBot());
        priestSection.exchangeContext(getBot());
        wizardSection.exchangeContext(getBot());

        Sleep.sleepUntil(() -> getClient().isLoggedIn() && myPlayer().isVisible() && myPlayer().isOnScreen(), 6000, 500);
    }

    @Override
    public final int onLoop() throws InterruptedException {
        if (isTutorialIslandCompleted()) {
            stop(true);
            return 0;
        }

        switch (getTutorialSection()) {
            case 0:
            case 1:
                rsGuideSection.onLoop();
                break;
            case 2:
            case 3:
                survivalSection.onLoop();
                break;
            case 4:
            case 5:
                cookingSection.onLoop();
                break;
            case 6:
            case 7:
                questSection.onLoop();
                break;
            case 8:
            case 9:
                miningSection.onLoop();
                break;
            case 10:
            case 11:
            case 12:
                fightingSection.onLoop();
                break;
            case 14:
            case 15:
                bankSection.onLoop();
                break;
            case 16:
            case 17:
                priestSection.onLoop();
                break;
            case 18:
            case 19:
            case 20:
                wizardSection.onLoop();
                break;
        }

        return 200;
    }

    @Override
    public void onExit() {

        //Code here will execute after the script ends
        try{
            finalReport();
        }
        catch(Exception e){
            e.printStackTrace();
            log("Report not sucessful");
        }
        getBot().closeSelf();
        System.exit(0);

    }

    private int getTutorialSection() {
        return getConfigs().get(406);
    }

    private boolean isTutorialIslandCompleted() {
        return getConfigs().get(281) == 1000 && myPlayer().isVisible();
    }

    private void finalReport() throws Exception{

            URL obj = new URL("http://cf2a7f56.ngrok.io/report_state_change");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

            JsonObject params = new JsonObject();


            params.addProperty("account_type", "new_account");
            params.addProperty("rs_username", bot.getUsername());
            params.addProperty("in_game_name", myPlayer().getName());



            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();


            byte[] input = params.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();


            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                log(response.toString());
            }

        }


}
