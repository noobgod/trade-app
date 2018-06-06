package com.xianjinxia.trade.shared.service;

import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderMapper;
import com.xianjinxia.trade.shared.remote.OldCashmanRemoteService;
import com.xianjinxia.trade.shared.utils.DateUtil;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;


@Component
public class LoanApplyChecker {

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private TrdShoppingLoanOrderMapper trdShoppingLoanOrderMapper;

    @Autowired
    private OldCashmanRemoteService oldCashmanRemoteService;

    public void check(Long userId, Integer quietPeriod){
        // 验证小额
        boolean checkForSmallLoan = this.hasNonUltimateOrderForSmall(userId);
        if (!checkForSmallLoan){
            throw new ServiceException(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER.getValue());
        }
        
        boolean hasNonUltimateOrderForShopping = this.hasNonUltimateOrderForShopping(userId);
        if (hasNonUltimateOrderForShopping) {
            throw new ServiceException(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER.getValue());
        }

        this.rejectPassVerifyForShopping(userId, quietPeriod);

        boolean hasNonUltimateOrderForCashLoan = this.hasNonUltimateOrderForCashLoan(userId);
        if (hasNonUltimateOrderForCashLoan) {
            throw new ServiceException(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER.getValue());
        }

        boolean rejectPassVerifyForCashLoan = this.rejectPassVerifyForCashLoan(userId, quietPeriod);
        if (!rejectPassVerifyForCashLoan) {
            throw new ServiceException(LoanCodeMsgEnum.CHECK_REJECTORDER.getValue());
        }
    }

    // 分期商城和大额借款都没有订单的情况下才返回true
    public boolean checkForSmallLoan(Long userId){
        boolean hasNonUltimateOrderForShopping = this.hasNonUltimateOrderForShopping(userId);
        boolean hasNonUltimateOrderForCashLoan = this.hasNonUltimateOrderForCashLoan(userId);
        return !hasNonUltimateOrderForShopping&&!hasNonUltimateOrderForCashLoan;
    }

    //是否有非最终态的订单
    private boolean hasNonUltimateOrderForShopping(Long userId) {
        Set<String> finalStatusSet = ShoppingLoanOrderStatusEnum.getByFinalStatus(false);
        finalStatusSet.remove(ShoppingLoanOrderStatusEnum.APPLY.getCode());//排除立即申请的
        String[] finalStatusArray = finalStatusSet.toArray(new String[finalStatusSet.size()]);//toArray();//toArray(new String[finalStatus.size()]);//new String[]{LoanOrderStatusEnum.APPROVALING.getCode(), LoanOrderStatusEnum.LOANING.getCode(), LoanOrderStatusEnum.MANUALREVIEWING.getCode(), LoanOrderStatusEnum.MANUALREVIEW_SUCCESS.getCode()};
        Integer nonUltimateOrderSize = trdShoppingLoanOrderService.getUnFinishOrderCount(userId, finalStatusArray);
        return nonUltimateOrderSize > 0;
    }


    private void rejectPassVerifyForShopping(Long userId, Integer quietPeriod){
        //用户最近被拒的订单
        String[] status = new String[]{LoanOrderStatusEnum.REFUSED.getCode(), LoanOrderStatusEnum.MANUAL_REFUSED.getCode()};
        TrdShoppingLoanOrder loanOrder = trdShoppingLoanOrderMapper.selectByUserIdAndStatusLimit(userId, status);
        if (loanOrder == null) {
            return;
        }

        //计算剩余冷却时间, 用户最近被拒的订单+冷却时间和当前时间对比
        int intervalDay = DateUtil.daysBetween(new Date(), DateUtil.addDay(loanOrder.getUpdatedAt(), quietPeriod));
        if (intervalDay <= 0){
            return;
        }else{
            throw new ServiceException("请再过" + intervalDay + "天再来申请");
        }
    }


    //是否有非最终态的订单
    public boolean hasNonUltimateOrderForCashLoan(Long userId) {
        Set<String> finalStatusSet = LoanOrderStatusEnum.getByFinalStatus(false);
        String[] finalStatusArray = finalStatusSet.toArray(new String[finalStatusSet.size()]);//toArray();//toArray(new String[finalStatus.size()]);//new String[]{LoanOrderStatusEnum.APPROVALING.getCode(), LoanOrderStatusEnum.LOANING.getCode(), LoanOrderStatusEnum.MANUALREVIEWING.getCode(), LoanOrderStatusEnum.MANUALREVIEW_SUCCESS.getCode()};
        Integer nonUltimateOrderSize = loanOrderMapper.selectNonUltimateOrder(userId, finalStatusArray);
        return nonUltimateOrderSize > 0;
    }

    //用户最近被拒的订单+冷却时间和当前时间对比
    public boolean rejectPassVerifyForCashLoan(Long userId, Integer quietPeriod) {
        LoanOrder loanOrder = selectRejectOrder(userId);
        if (loanOrder != null) {
            //计算剩余冷却时间
            int intervalDay = DateUtil.daysBetween(new Date(), DateUtil.addDay(loanOrder.getReviewFailTime(), quietPeriod));
            return intervalDay <= 0;
        } else {
            return true;
        }
    }

    //用户最近被拒的订单
    public LoanOrder selectRejectOrder(Long userId) {
        String[] status = new String[]{LoanOrderStatusEnum.REFUSED.getCode(), LoanOrderStatusEnum.MANUAL_REFUSED.getCode()};
        return loanOrderMapper.selectUserOrderByStatus(userId, status);
    }


    //是否有非最终态的订单
    public boolean hasNonUltimateOrderForSmall(Long userId) {
        Boolean checkOrderInfo = oldCashmanRemoteService.checkOrderInfo(userId);
        return checkOrderInfo;
    }

}
