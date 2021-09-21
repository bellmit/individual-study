package cn.gyw.backend.search.controller;

import cn.gyw.backend.search.domain.EsProduct;
import cn.gyw.backend.search.domain.EsProductRelatedInfo;
import cn.gyw.backend.search.service.EsProductService;
import cn.gyw.backend.web.enums.CommonRespEnum;
import cn.gyw.backend.web.model.BaseResponse;
import cn.gyw.backend.web.model.DataResponse;
import cn.gyw.backend.web.model.PageData;
import cn.gyw.backend.web.utils.SpringDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索商品管理
 */
@RestController
@RequestMapping("/esProduct")
public class EsProductController {

    @Autowired
    private EsProductService esProductService;

    @PostMapping(value = "/importAll")
    public DataResponse<Integer> importAllList() {
        int count = esProductService.importAll();
        return DataResponse.success(count);
    }

    @GetMapping(value = "/delete/{id}")
    public DataResponse<Object> delete(@PathVariable Long id) {
        esProductService.delete(id);
        return DataResponse.success(null);
    }

    @PostMapping(value = "/delete/batch")
    public DataResponse<Object> delete(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return DataResponse.success(null);
    }

    @PostMapping(value = "/create/{id}")
    public BaseResponse create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return DataResponse.success(esProduct);
        } else {
            return DataResponse.error(CommonRespEnum.NO_DATA);
        }
    }

    /**
     * 简单搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/search/simple")
    public DataResponse<PageData<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return DataResponse.success(SpringDataUtil.resetPage(esProductPage));
    }

    // 综合搜索、筛选、排序
    // @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
    //         defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public DataResponse<PageData<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) Long brandId,
                                                      @RequestParam(required = false) Long productCategoryId,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return DataResponse.success(SpringDataUtil.resetPage(esProductPage));
    }

    // 根据商品id推荐商品
    @GetMapping(value = "/recommend/{id}")
    public DataResponse<PageData<EsProduct>> recommend(@PathVariable Long id,
                                                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                         @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        return DataResponse.success(SpringDataUtil.resetPage(esProductPage));
    }

    //获取搜索的相关品牌、分类及筛选属性
    @GetMapping(value = "/search/relate")
    public DataResponse<EsProductRelatedInfo> searchRelatedInfo(@RequestParam(required = false) String keyword) {
        EsProductRelatedInfo productRelatedInfo = esProductService.searchRelatedInfo(keyword);
        return DataResponse.success(productRelatedInfo);
    }
}
