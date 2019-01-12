package gamblermod.character;

import java.util.ArrayList;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gamblermod.cards.attacks.Strike;
import gamblermod.enumPatches.AbstractCardEnum;
import gamblermod.enumPatches.GamblerClassEnum;

public class GamblerCharacter extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
    public static final String MY_CHARACTER_SHOULDER_2 = "gamblermod/img/character/Gambler-Shoulder.png"; // campfire pose
    public static final String MY_CHARACTER_SHOULDER_1 = "gamblermod/img/character/Gambler-Shoulder.png"; // another campfire pose
    public static final String MY_CHARACTER_CORPSE = "gamblermod/img/character/Gambler-Corpse.png"; // dead corpse
    public static final String MY_CHARACTER_SKELETON_ATLAS = "gamblermod/img/character/Gambler.atlas"; // spine animation atlas
    public static final String MY_CHARACTER_SKELETON_JSON = "gamblermod/img/character/Gambler.json"; // spine animation json

    public static final String[] ORB_STRINGS =  { "gamblermod/img/character/Orbs/layer1.png", "gamblermod/img/character/Orbs/layer2.png", "gamblermod/img/character/Orbs/layer3.png", "gamblermod/img/character/Orbs/layer4.png", "gamblermod/img/character/Orbs/layer5.png", "gamblermod/img/character/Orbs/layer6.png", "gamblermod/img/character/Orbs/layer1d.png", "gamblermod/img/character/Orbs/layer2d.png", "gamblermod/img/character/Orbs/layer3d.png", "gamblermod/img/character/Orbs/layer4d.png", "gamblermod/img/character/Orbs/layer5d.png" };

    public GamblerCharacter (String name) {
        super(name, GamblerClassEnum.GAMBLER_CLASS, ORB_STRINGS, "gamblermod/img/character/Orbs/energyGamblerVFX.png", (String)null, (String)null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

        initializeClass(null, MY_CHARACTER_SHOULDER_2, // required call to load textures and setup energy/loadout
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(MY_CHARACTER_SKELETON_ATLAS, MY_CHARACTER_SKELETON_JSON, 2.0F); // if you're using modified versions of base game animations or made animations in spine make sure to include this bit and the following lines

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
        ArrayList<String> retVal = new ArrayList<>();
        for(int i = 0; i < 5; i++)
        {
            retVal.add("gamblermod:Strike_Gambler");
        }
        for(int i = 0; i < 4; i++)
        {
            retVal.add("gamblermod:Defend_Gambler");
        }
        retVal.add("gamblermod:LuckyCoin");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("gamblermod:joker");
        UnlockTracker.markRelicAsSeen("gamblermod:joker");
        return retVal;
    }

    public static final int STARTING_HP = 65;
    public static final int MAX_HP = 65;
    public static final int STARTING_GOLD = 149;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 0;

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Gambler", "A roaming trickster who made his living cheating at games of chance.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "the Gambler";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.Gambler_CHECKERBOARD;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~lost~ ~one,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.PURPLE;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_LIGHT", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Gambler";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new GamblerCharacter(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return "NL You ready your final trick...";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.PURPLE;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};
    }
}
