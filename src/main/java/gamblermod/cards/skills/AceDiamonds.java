package gamblermod.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gamblermod.enumPatches.AbstractCardEnum;

public class AceDiamonds extends CustomCard {

    public static final String ID = "gamblermod:AceDiamonds";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // Get object containing the strings that are displayed in the game.
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "gamblermod/img/cards/defend.png";
    private static final int COST = 1;
    private static final int BLOCK = 2;
    private static final int BLOCK_UPGRADE = 2;

    public AceDiamonds() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.Gambler_CHECKERBOARD,
                CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int skills = 0;
        for(AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if(card.type == CardType.SKILL)
                skills++;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AceDiamonds();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(BLOCK_UPGRADE);
        }
    }
}
