package org.ds.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.service.SetmealService;
import org.ds.health.utils.COSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/9 19:41
 * @version: 1.0
 */
@Component
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 清理七牛上的垃圾图片
     */
    //@Scheduled(cron = "0 0 4 * * ?")
    @Scheduled(initialDelay = 3000,fixedDelay = 1800000)
    public void cleanImg() {
        log.info("开发执行清理垃圾图片....");
        //1 COSUtil.查询所有的图片
        List<String> imgInCos = COSUtil.listFile();
        // {} 占位符
        log.debug("云存储上一共有{}张图片", imgInCos.size());
        //2 调用setmealService查询数据库的所有图片
        List<String> imgInDb = setmealService.findImgs();
        log.debug("数据库里一共有{}张图片", imgInDb == null ? 0 : imgInDb.size());
        //3 把云上的减去数据库的，剩下的就是要删除的图片
        imgInCos.removeAll(Objects.requireNonNull(imgInDb));
        if (imgInCos.size() > 0) {
            log.debug("要清理的垃圾图片有{}张", imgInCos.size());
            COSUtil.removeFiles(imgInCos);
        } else {
            log.debug("没有需要清理的垃圾图片");
        }
        log.info("清理垃圾图片完成....");
    }

}
