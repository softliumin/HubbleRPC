package cc.sharper.controller;

import cc.sharper.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by liumin3 on 2016/9/8.
 */
@Controller
@RequestMapping("/")
public class IndexController
{
    private final static Logger log = LoggerFactory.getLogger(NetUtils.class);


    @RequestMapping("")
    public String index()
    {

        return "";
    }
}
