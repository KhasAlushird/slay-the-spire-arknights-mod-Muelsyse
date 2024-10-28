package muelmod.modcore;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import static basemod.BaseMod.logger;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import muelmod.character.MuelSyse;
import static muelmod.character.MuelSyse.PlayerColorEnum.MUEL_COLOR;
import static muelmod.character.MuelSyse.PlayerColorEnum.MUEL_KHAS_CHARACTER;
import muelmod.helpers.ImageHelper;
import muelmod.potions.BottledBioPotion;
import muelmod.potions.CushionPotion;
import muelmod.potions.EssenceOfEnergyFluidPotion;
import muelmod.relics.CoconutShell;
import muelmod.relics.EcoSpecimen;
import muelmod.relics.EmergencyEcoDevice;
import muelmod.relics.ExuberantPlant;
import muelmod.relics.FluidGetter;
import muelmod.relics.OceanVoice;

@SpireInitializer
public class TheCore implements EditCardsSubscriber,EditStringsSubscriber,EditCharactersSubscriber,EditRelicsSubscriber,EditKeywordsSubscriber{  
    // 实现接口
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON =   ImageHelper.getOtherImgPath("character","Character_Button" );
    private static final String MY_CHARACTER_PORTRAIT = ImageHelper.getOtherImgPath("character","Character_Portrait" );
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_attack_512");
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_power_512");
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_skill_512");
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = ImageHelper.getOtherImgPath("character","small_orb");
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_attack_1024");
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_power_1024");
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = ImageHelper.getImgPathWithSubType("character", "cardback","bg_skill_1024");
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = ImageHelper.getOtherImgPath("character","big_orb");
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = ImageHelper.getOtherImgPath("character","energy_orb");
    public static final Color MY_COLOR = new Color(207.0F / 255.0F, 249.0F / 255.0F, 243F / 255.0F, 1.0F);

    public TheCore() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
        // 这里注册颜色
        BaseMod.addColor(MUEL_COLOR, MY_COLOR,
         MY_COLOR, MY_COLOR,
          MY_COLOR, MY_COLOR,
           MY_COLOR, MY_COLOR,
           BG_ATTACK_512,BG_SKILL_512,
           BG_POWER_512,ENEYGY_ORB,
           BG_ATTACK_1024,BG_SKILL_1024,
           BG_POWER_1024,BIG_ORB,
           SMALL_ORB);
    }

    public static void initialize() {
        new TheCore();
    }

    // 当basemod开始注册mod卡牌时，便会调用这个函数
    @Override
    public void receiveEditCards() {
        // TODO 这里写添加你卡牌的代码
        basemod.BaseMod.addCard(new muelmod.cards.Strike());
        basemod.BaseMod.addCard(new muelmod.cards.WaterStrike());
        basemod.BaseMod.addCard(new muelmod.cards.EcoloBlock());
        basemod.BaseMod.addCard(new muelmod.cards.Turbulence());
        basemod.BaseMod.addCard(new muelmod.cards.Mould());
        basemod.BaseMod.addCard(new muelmod.cards.FluidShot());
        basemod.BaseMod.addCard(new muelmod.cards.Transparent());
        basemod.BaseMod.addCard(new muelmod.cards.HiPreWaterCannon());
        basemod.BaseMod.addCard(new muelmod.cards.Bubble());
        basemod.BaseMod.addCard(new muelmod.cards.Defend());
        basemod.BaseMod.addCard(new muelmod.cards.Hydroponics());
        basemod.BaseMod.addCard(new muelmod.cards.BotanicGarden());
        basemod.BaseMod.addCard(new muelmod.cards.WildGrow());
        basemod.BaseMod.addCard(new muelmod.cards.SeedMine());
        basemod.BaseMod.addCard(new muelmod.cards.AcidRain());
        basemod.BaseMod.addCard(new muelmod.cards.Spring());
        basemod.BaseMod.addCard(new muelmod.cards.Liquify());
        basemod.BaseMod.addCard(new muelmod.cards.Sow());
        basemod.BaseMod.addCard(new muelmod.cards.ThornArmor());
        basemod.BaseMod.addCard(new muelmod.cards.PlantSmash());
        basemod.BaseMod.addCard(new muelmod.cards.EcoDominance());
        basemod.BaseMod.addCard(new muelmod.cards.AcuteLubrication());
        basemod.BaseMod.addCard(new muelmod.cards.CoordinatedAttack());
        basemod.BaseMod.addCard(new muelmod.cards.RippleVibration());
        basemod.BaseMod.addCard(new muelmod.cards.NonEntropyAdaptation());
        basemod.BaseMod.addCard(new muelmod.cards.WaterBlade());
        basemod.BaseMod.addCard(new muelmod.cards.HoldLine());
        basemod.BaseMod.addCard(new muelmod.cards.StrenAndDex());
        basemod.BaseMod.addCard(new muelmod.cards.SandStabilization());
        basemod.BaseMod.addCard(new muelmod.cards.Afforestation());
        basemod.BaseMod.addCard(new muelmod.cards.RidingWind());
        basemod.BaseMod.addCard(new muelmod.cards.Assembly());
        basemod.BaseMod.addCard(new muelmod.cards.WoodlandStream());
        basemod.BaseMod.addCard(new muelmod.cards.ForestGuard());
        basemod.BaseMod.addCard(new muelmod.cards.Regenerate());
        basemod.BaseMod.addCard(new muelmod.cards.CutExpenditure());
        basemod.BaseMod.addCard(new muelmod.cards.VineAmbush());
        basemod.BaseMod.addCard(new muelmod.cards.GiantWave());
        basemod.BaseMod.addCard(new muelmod.cards.CreativeExperiment());
        basemod.BaseMod.addCard(new muelmod.cards.SunBeam());
        basemod.BaseMod.addCard(new muelmod.cards.Tide());
        basemod.BaseMod.addCard(new muelmod.cards.LeafShield());
        basemod.BaseMod.addCard(new muelmod.cards.Gardening());
        basemod.BaseMod.addCard(new muelmod.cards.Cambrian());
        basemod.BaseMod.addCard(new muelmod.cards.Lubricant());
        basemod.BaseMod.addCard(new muelmod.cards.Gurgling());
        basemod.BaseMod.addCard(new muelmod.cards.SlowAndSteady());
        basemod.BaseMod.addCard(new muelmod.cards.Solidify());
        basemod.BaseMod.addCard(new muelmod.cards.WaterIsLife());
        basemod.BaseMod.addCard(new muelmod.cards.LeafHurricane());
        basemod.BaseMod.addCard(new muelmod.cards.PottedPlant());
        basemod.BaseMod.addCard(new muelmod.cards.Reproduction());
        basemod.BaseMod.addCard(new muelmod.cards.RapidEjection());
        basemod.BaseMod.addCard(new muelmod.cards.Nurture());
        basemod.BaseMod.addCard(new muelmod.cards.Clear());
        basemod.BaseMod.addCard(new muelmod.cards.StillWater());
        basemod.BaseMod.addCard(new muelmod.cards.StandingWater());
        basemod.BaseMod.addCard(new muelmod.cards.Charge());
        basemod.BaseMod.addCard(new muelmod.cards.Exhaustion());
        basemod.BaseMod.addCard(new muelmod.cards.WildFire());
        
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == GameLanguage.ZHS) {
            lang = "zhs"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "eng"; // 如果没有相应语言的版本，默认加载英语
        }

        logger.info("Loading localization files for language: " + lang);

        try {
            String cardsPath = "muelmod/localization/" + lang + "/cards.json";
            if (Gdx.files.internal(cardsPath).exists()) {
                BaseMod.loadCustomStringsFile(CardStrings.class, cardsPath);
                logger.info("Loaded cards.json");
            } else {
                logger.error("File not found: " + cardsPath);
            }

            String charactersPath = "muelmod/localization/" + lang + "/characters.json";
            if (Gdx.files.internal(charactersPath).exists()) {
                BaseMod.loadCustomStringsFile(CharacterStrings.class, charactersPath);
                logger.info("Loaded characters.json");
            } else {
                logger.error("File not found: " + charactersPath);
            }

            String powersPath = "muelmod/localization/" + lang + "/powers.json";
            if (Gdx.files.internal(powersPath).exists()) {
                BaseMod.loadCustomStringsFile(PowerStrings.class, powersPath);
                logger.info("Loaded powers.json");
            } else {
                logger.error("File not found: " + powersPath);
            }

            String relicsPath = "muelmod/localization/" + lang + "/relics.json";
            if (Gdx.files.internal(relicsPath).exists()) {
                BaseMod.loadCustomStringsFile(RelicStrings.class, relicsPath);
                logger.info("Loaded relics.json");
            } else {
                logger.error("File not found: " + relicsPath);
            }

            String potionsPath = "muelmod/localization/" + lang + "/potions.json";
            if (Gdx.files.internal(potionsPath).exists()) {
                BaseMod.loadCustomStringsFile(PotionStrings.class, potionsPath);
                logger.info("Loaded potions.json");
            } else {
                logger.error("File not found: " + potionsPath);
            }
        } catch (Exception e) {
            logger.error("Failed to load localization files for language: " + lang, e);
        }
    }
    
    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new MuelSyse(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MUEL_KHAS_CHARACTER);
    }

   
    @Override
    public void receiveEditRelics() {

        //register relics here
        // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
        BaseMod.addRelicToCustomPool(new EcoSpecimen(),MuelSyse.PlayerColorEnum.MUEL_COLOR);
        BaseMod.addRelicToCustomPool(new ExuberantPlant(), MuelSyse.PlayerColorEnum.MUEL_COLOR);
        BaseMod.addRelicToCustomPool(new EmergencyEcoDevice(), MuelSyse.PlayerColorEnum.MUEL_COLOR);
        BaseMod.addRelicToCustomPool(new CoconutShell(), MuelSyse.PlayerColorEnum.MUEL_COLOR);
        BaseMod.addRelicToCustomPool(new FluidGetter(), MuelSyse.PlayerColorEnum.MUEL_COLOR);
        BaseMod.addRelicToCustomPool(new OceanVoice(), MuelSyse.PlayerColorEnum.MUEL_COLOR);


        //register potions here
        BaseMod.addPotion(CushionPotion.class, Color.GREEN, Color.YELLOW, Color.CLEAR, "MuelSyseKhas:CushionPotion", MUEL_KHAS_CHARACTER);
        BaseMod.addPotion(BottledBioPotion.class, Color.GREEN, Color.GREEN, Color.FOREST, "MuelSyseKhas:BottledBioPotion", MUEL_KHAS_CHARACTER);
        BaseMod.addPotion(EssenceOfEnergyFluidPotion.class, Color.PINK, Color.PURPLE, Color.CLEAR, "MuelSyseKhas:EssenceOfEnergyFluidPotion", MUEL_KHAS_CHARACTER);
        


}


@Override
public void receiveEditKeywords() {
    Gson gson = new Gson();
    String lang = "eng"; 
    if (Settings.language == Settings.GameLanguage.ZHS) {
        lang = "zhs";
    }
    logger.info("Loading keywords for language: " + lang);

    String json = Gdx.files.internal("muelmod/localization/" + lang + "/keywords.json")
            .readString(String.valueOf(StandardCharsets.UTF_8));
    Keyword[] keywords = gson.fromJson(json, Keyword[].class);
    if (keywords != null) {
        for (Keyword keyword : keywords) {
            // 这个id要全小写
            BaseMod.addKeyword("muelsysekhas", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            logger.info("Loaded keyword: " + keyword.NAMES[0]);
        }
    } else {
        logger.warn("No keywords found for language: " + lang);
    }
}
}
