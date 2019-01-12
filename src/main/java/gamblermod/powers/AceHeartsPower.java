package gamblermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gamblermod.actions.AceHeartsAction;

public class AceHeartsPower extends AbstractPower
{
    public static final String POWER_ID = "gamblermod:AceHeartsPower";
    public static final PowerStrings powerInfo = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerInfo.NAME;
    public static final String [] DESCRIPTIONS = powerInfo.DESCRIPTIONS;
    public static final String IMG = "gamblermod/img/powers/AceHeartsPowerS.png";

    public AceHeartsPower(AbstractCreature owner, int stacks)
    {
        this.img = ImageMaster.loadImage(IMG);
        this.name = NAME;

        this.owner = owner;

        this.type = PowerType.BUFF;
        ID = POWER_ID;

        this.amount = stacks;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.amount += stackAmount;
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        AbstractDungeon.actionManager.addToBottom(new AceHeartsAction(owner, this.amount, this));
    }
}
