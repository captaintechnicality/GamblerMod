package gamblermod.cards.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gamblermod.enumPatches.AbstractCardEnum;
import gamblermod.powers.HeartOfGoldPower;

public class HeartOfGold extends CustomCard
{

    public static final String ID = "gamblermod:HeartOfGold";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // Get object containing the strings that are displayed in the game.
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "gamblermod/img/cards/HeartOfGold.png";
    private static final int COST = 0;
    public static final int GOLD_COST = 5;
    public static final int GOLD_DISCOUNT = 2;

    public HeartOfGold()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.Gambler_CHECKERBOARD,
                CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = GOLD_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HeartOfGoldPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new HeartOfGold();
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-GOLD_DISCOUNT);
        }
    }
}
