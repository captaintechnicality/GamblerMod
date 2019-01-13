package gamblermod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import gamblermod.cards.attacks.*;
import gamblermod.cards.powers.AceHearts;
import gamblermod.cards.powers.SmuggleCards;
import gamblermod.cards.skills.*;
import gamblermod.character.GamblerCharacter;
import gamblermod.enumPatches.AbstractCardEnum;
import gamblermod.enumPatches.GamblerClassEnum;
import gamblermod.relics.Joker;

@SpireInitializer
public class GamblerMod implements EditCardsSubscriber, EditStringsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber{

    public GamblerMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new GamblerMod();
        BaseMod.addColor(AbstractCardEnum.Gambler_CHECKERBOARD, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.PURPLE, "gamblermod/img/cardbg/bg_attack_checker_small.png", "gamblermod/img/cardbg/bg_skill_checker_small.png", "gamblermod/img/cardbg/bg_power_checker_small.png", "gamblermod/img/cardbg/card_checker_orb_small.png", "gamblermod/img/cardbg/bg_attack_checker.png", "gamblermod/img/cardbg/bg_skill_checker.png", "gamblermod/img/cardbg/bg_power_checker.png", "gamblermod/img/cardbg/card_checker_orb.png", "gamblermod/img/cardbg/card_checker_orb.png");
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new LuckyCoin());
        // REWARD CARDS
        BaseMod.addCard(new ClubPair(0));
        BaseMod.addCard(new DiamondPair(0));
        BaseMod.addCard(new HighCard(0));
        // ACES
        BaseMod.addCard(new AceClubs());
        BaseMod.addCard(new AceDiamonds());
        BaseMod.addCard(new AceHearts());
        // TENS
        BaseMod.addCard(new TenClubs());
        BaseMod.addCard(new TenDiamonds());
        // MISC POWERS
        BaseMod.addCard(new SmuggleCards());
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new Joker(), AbstractCardEnum.Gambler_CHECKERBOARD);
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "gamblermod/localization/Gambler-CardStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "gamblermod/localization/Gambler-RelicStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "gamblermod/localization/Gambler-PowerStrings.json");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new GamblerCharacter(CardCrawlGame.playerName),
                "gamblermod/img/ui/gamblerButton.png",
                "gamblermod/img/ui/gamblerPortrait.jpg",
                GamblerClassEnum.GAMBLER_CLASS
        );
    }
}