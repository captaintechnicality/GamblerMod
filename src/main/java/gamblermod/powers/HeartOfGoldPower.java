package gamblermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gamblermod.cards.powers.HeartOfGold;

public class HeartOfGoldPower extends AbstractPower
{
    public static final String POWER_ID = "gamblermod:HeartOfGoldPower";
    public static final PowerStrings powerInfo = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerInfo.NAME;
    public static final String[] DESCRIPTIONS = powerInfo.DESCRIPTIONS;
    public static final String IMG = "gamblermod/img/powers/HeartOfGoldPowerS.png";

    private int drawPower = 0;

    private int regStacks = 0;
    private int upStacks = 0;

    public HeartOfGoldPower(AbstractCreature owner, int stacks)
    {
        this.img = ImageMaster.loadImage(IMG);
        this.name = NAME;

        this.owner = owner;

        this.type = PowerType.BUFF;
        ID = POWER_ID;

        this.amount = stacks;

        drawPower = 1;

        if(stacks == HeartOfGold.GOLD_COST)
            regStacks++;
        else
            upStacks++;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.amount += stackAmount;
        drawPower++;
        if(stackAmount == HeartOfGold.GOLD_COST)
            regStacks++;
        else
            upStacks++;
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.drawPower + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn()
    {
        int UpgradeGold = HeartOfGold.GOLD_COST - HeartOfGold.GOLD_DISCOUNT;
        for(int i = 0; i < upStacks; i++)
        {
            if(AbstractDungeon.player.gold >= UpgradeGold)
            {
                Hitbox playerBox = AbstractDungeon.player.hb;
                for(int j = 0; j < UpgradeGold; j++)
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.owner, playerBox.cX, playerBox.cY,
                            playerBox.cX, playerBox.cY - 100 * Settings.scale, false));
                AbstractDungeon.player.loseGold(UpgradeGold);
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, 1));
            }
        }
        for(int i = 0; i < regStacks; i++)
        {
            if(AbstractDungeon.player.gold >= HeartOfGold.GOLD_COST)
            {
                Hitbox playerBox = AbstractDungeon.player.hb;
                for(int j = 0; j < HeartOfGold.GOLD_COST; j++)
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.owner, playerBox.cX, playerBox.cY,
                            playerBox.cX, playerBox.cY - 100 * Settings.scale, false));
                AbstractDungeon.player.loseGold(HeartOfGold.GOLD_COST);
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, 1));
            }
        }
    }
}