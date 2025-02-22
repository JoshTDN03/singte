package cn.sixlab.minesoft.singte.core.dao;

import cn.hutool.core.util.StrUtil;
import cn.sixlab.minesoft.singte.core.common.config.BaseDao;
import cn.sixlab.minesoft.singte.core.common.pager.PageResult;
import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.models.StArticle;
import cn.sixlab.minesoft.singte.core.models.StCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class StArticleDao extends BaseDao<StArticle> {

    @Override
    public Class<StArticle> entityClass() {
        return StArticle.class;
    }

    public List<StArticle> selectHot(int size) {
        Query query = new Query(Criteria.where("status").is(StConst.YES))
                .with(Sort.by(Sort.Direction.DESC, "thumbCount"))
                .limit(size);
        return mongoTemplate.find(query, StArticle.class);
    }

    public List<StArticle> selectLast(int size) {
        Query query = new Query(Criteria.where("status").is(StConst.YES))
                .with(Sort.by(Sort.Direction.DESC, "publishTime"))
                .limit(size);
        return mongoTemplate.find(query, StArticle.class);
    }

    public List<StArticle> selectRandom(int size) {
        Criteria criteria = Criteria.where("status").is(StConst.YES);
        MatchOperation matchOperation = Aggregation.match(criteria);
        SampleOperation sizeOperation = Aggregation.sample(size);
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, sizeOperation);
        AggregationResults<StArticle> output = mongoTemplate.aggregate(aggregation, StArticle.class, StArticle.class);

        return output.getMappedResults();
    }

    public List<StArticle> selectView(int size) {
        Query query = new Query(Criteria.where("status").is(StConst.YES))
                .with(Sort.by(Sort.Direction.DESC, "viewCount"))
                .limit(size);
        return mongoTemplate.find(query, StArticle.class);
    }

    public PageResult<StArticle> selectByCategory(String category, int pageNum, int pageSize) {
        Criteria criteria = Criteria.where("status").is(StConst.YES);
        if(StrUtil.isNotEmpty(category)){
            criteria = criteria.and("category").is(category);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "publishTime");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StArticle.class, pageNum, pageSize);
    }

    public PageResult<StArticle> selectByKeyword(String keyword, int pageNum, int pageSize) {
        Criteria criteria = Criteria.where("status").is(StConst.YES);
        if (StrUtil.isNotEmpty(keyword)) {
            criteria = criteria.and("keywords").is(keyword);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "publishTime");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StArticle.class, pageNum, pageSize);
    }

    public StArticle selectByAlias(String alias) {
        Criteria criteria = Criteria
                .where("status").is(StConst.YES)
                .and("alias").is(alias);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        Query query = new Query(criteria).with(sort).limit(1);

        return mongoTemplate.findOne(query, StArticle.class);
    }

    @Override
    public StArticle selectExist(StArticle record) {
        Criteria criteria = Criteria.where("alias").is(record.getAlias());

        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        Query query = new Query(criteria).with(sort).limit(1);

        return mongoTemplate.findOne(query, StArticle.class);
    }

    public void addView(String id) {
        StArticle article = selectById(id);
        article.setViewCount(article.getViewCount() + 1);
        save(article);
    }

    public PageResult<StArticle> selectByDate(Date begin, Date end, int pageNum, int pageSize) {
        Criteria criteria = Criteria.where("status").is(StConst.YES)
                .and("publishTime").gte(begin).lt(end);
        Sort sort = Sort.by(Sort.Direction.DESC, "publishTime");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StArticle.class, pageNum, pageSize);
    }

    public StArticle selectBySourceUrl(String sourceUrl) {
        Criteria criteria = Criteria.where("sourceUrl").is(sourceUrl);

        Query query = new Query(criteria).limit(1);

        return mongoTemplate.findOne(query, StArticle.class);
    }

    public List<StCategory> countCategory() {
        MatchOperation queryOperation = Aggregation.match(Criteria.where("status").is(StConst.YES));
        GroupOperation groupOperation = Aggregation.group("category").count().as("count");
        ProjectionOperation nameOperation = Aggregation.project("count").and("category").previousOperation();

        Aggregation aggregation = Aggregation.newAggregation(queryOperation, groupOperation, nameOperation);
        AggregationResults<StCategory> output = mongoTemplate.aggregate(aggregation, StArticle.class, StCategory.class);

        return output.getMappedResults();
    }

    public PageResult<StArticle> queryByWord(String word, int pageNum, int pageSize) {
        // TODO 待实现更好搜索方法
        Criteria criteria = Criteria.where("status").is(StConst.YES);
        if (StrUtil.isNotEmpty(word)) {
            criteria = criteria.and("content").regex(word);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "publishTime");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StArticle.class, pageNum, pageSize);
    }

    @Override
    public PageResult<StArticle> queryData(String keyword, String status, Integer pageNum, Integer pageSize) {
        Criteria criteria = new Criteria();
        if (StrUtil.isNotEmpty(status)) {
            criteria = criteria.and("status").is(status);
        }

        if (StrUtil.isNotEmpty(keyword)) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("alias").regex(keyword),
                    Criteria.where("sourceUrl").regex(keyword),
                    Criteria.where("title").regex(keyword),
                    Criteria.where("summary").regex(keyword),
                    Criteria.where("category").regex(keyword),
                    Criteria.where("content").regex(keyword),
                    Criteria.where("author").regex(keyword)
            );

            criteria = criteria.andOperator(keywordCriteria);
        }
        Sort sort = Sort.by("publishTime").descending();

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StArticle.class, pageNum, pageSize);
    }
}