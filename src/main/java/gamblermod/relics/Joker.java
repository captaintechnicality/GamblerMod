package gamblermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gamblermod.actions.PokerHandCheck;

public class Joker extends CustomRelic {

    public static String ID = "gamblermod:joker";
    boolean turnStart = false;
    int cardsDrawn = 0;

    public Joker()
    {
        super(ID, new Texture("gamblermod/img/relics/Joker.png"), RelicTier.STARTER, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Joker();
    }

    @Override
    public void atTurnStartPostDraw() {

        flash();
        AbstractDungeon.actionManager.addToBottom(new PokerHandCheck());
    }
}
