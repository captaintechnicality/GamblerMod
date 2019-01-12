package gamblermod.cards.skills;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gamblermod.enumPatches.AbstractCardEnum;

public class DiamondPair extends CustomCard {

    public static final String ID = "gamblermod:DiamondPair";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "gamblermod/img/cards/defend.png";
    private static final int COST = 0;
    private static final int UPGRADE_BLOCK_AMOUNT = 2;
    private int PairValue;

    public DiamondPair(int Value)
    {
        super( ID , NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.Gambler_CHECKERBOARD,
                CardRarity.SPECIAL, CardTarget.SELF);
        PairValue = Value;
        this.baseBlock = PairValue;
        this.exhaust = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DiamondPair(PairValue);
    }

    @Override
    public void upgrade() {
        if(!upgraded)
        {
            this.upgradeName();
            this.baseBlock += UPGRADE_BLOCK_AMOUNT;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
