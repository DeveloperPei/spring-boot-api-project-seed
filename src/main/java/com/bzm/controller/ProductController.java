package com.bzm.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.bzm.core.Result;
import com.bzm.core.ResultGenerator;
import com.bzm.core.ServiceException;
import com.bzm.entity.CreateTbkParamVo;
import com.bzm.entity.SearchParamVo;
import com.bzm.util.ConstantUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "产品模块")
@RestController
@RequestMapping("product")
public class ProductController {

    @ApiOperation(value = "商品搜索", notes="根据名字分页查询")
    @PostMapping("searchProduct")
    public Result searchProduct(@RequestBody SearchParamVo searchParamVo){
        String url="https://eco.taobao.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient(url, ConstantUtils.APP_KEY, ConstantUtils.APP_SECRET);
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setPageNo(searchParamVo.getPageNum());
        req.setPageSize(searchParamVo.getPageSize());
        // 是否是天猫
        req.setIsTmall(true);
        req.setQ(searchParamVo.getContent());
        req.setAdzoneId(110265100304L);
        req.setHasCoupon(true);// 是否有优惠券
        // need_free_shipment 是否包邮
        // sort 排序  销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi），价格（price）
        req.setSort("tk_rate_des");
        try {
            TbkDgMaterialOptionalResponse rsp = client.execute(req);
            return ResultGenerator.genSuccessResult(rsp.getBody());
        } catch (ApiException e) {
            throw new  ServiceException(ExceptionUtil.getRootCauseMessage(e),e);
        }
    }

    @ApiOperation(value = "生成淘口令", notes="生成淘口令")
    @PostMapping("createTbk")
    public Result createTbk(@RequestBody CreateTbkParamVo createTbkVo){
        String url="http://gw.api.taobao.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient(url, ConstantUtils.APP_KEY, ConstantUtils.APP_SECRET);
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest ();

        req.setLogo(createTbkVo.getLogo());
        req.setText(createTbkVo.getText());
        req.setUrl(createTbkVo.getUrl());
        try {
             TbkTpwdCreateResponse rsp = client.execute(req);
            return ResultGenerator.genSuccessResult(rsp.getBody());
        } catch (ApiException e) {
            throw new  ServiceException(ExceptionUtil.getRootCauseMessage(e),e);
        }
    }
}
