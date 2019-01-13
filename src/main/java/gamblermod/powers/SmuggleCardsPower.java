package gamblermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gamblermod.actions.SmuggleCardsAction;

public class SmuggleCardsPower extends AbstractPower
{
    public static final String POWER_ID = "gamblermod:SmuggleCardsPower";
    public static final PowerStrings powerInfo = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerInfo.NAME;
    public static final String [] DESCRIPTIONS = powerInfo.DESCRIPTIONS;
    public static final String IMG = "gamblermod/img/powers/SmuggleCardsPowerS.png";

    public SmuggleCardsPower(AbstractCreature owner, int stacks)
    {
        this.img = ImageMaster.loadImage(IMG);
        this.owner = owner;
        this.amount = stacks;

        this.name = NAME;
        this.type = PowerType.BUFF;

        ID = POWER_ID;
        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        if(amount == 1)
        {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.amount += stackAmount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        for(int i = 0; i < amount; i++)
            AbstractDungeon.actionManager.addToTop(new SmuggleCardsAction(owner));
    }
}
