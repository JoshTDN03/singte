<#macro PageFrame title="" keywords=[] description="" static="" aside=false >
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <meta name="keywords" content="${keywords?join(",")}"/>
    <meta name="description" content="${description}"/>
    <link href="/static/cssmoban/css/base.css" rel="stylesheet">
    <link href="/static/cssmoban/css/m.css" rel="stylesheet">

    <script src="/static/plugins/jquery/jquery.min.js"></script>
    <script src="/static/cssmoban/js/main.js"></script>
    ${static}
</head>
<body>
<header>
    <div class="logo-section box">
        <div class="search">
            <form action="/search" method="get" name="searchform" id="searchform">
                <input name="keyboard" id="keyboard" class="input_text" value="请输入关键字词"
                       style="color: rgb(153, 153, 153);"
                       onfocus="if(value==='请输入关键字词'){this.style.color='#000';value=''}"
                       onblur="if(!value){this.style.color='#999';value='请输入关键字词'}" type="text">
                <input name="show" value="title" type="hidden">
                <input name="tempid" value="1" type="hidden">
                <input name="tbname" value="news" type="hidden">
                <input name="Submit" class="input_submit" value="搜索" type="submit">
            </form>
        </div>
        <div class="logo"><a href="#"><img alt="logo" src="<@StConfig key="st_site_logo" />">
                <h2 class="logo-title"><@StConfig key="st_site_name" /></h2>
                <p class="logo-text"><@StConfig key="st_site_subtitle" /></p>
            </a></div>
    </div>
    <div class="clear"></div>
    <div class="topnav">
        <h2><@StConfig key="st_site_name" /></h2>
        <nav>
            <ul id="starlist">
                <@StMenu group="nav">
                    <#list stMenuGroup as level1Menu>
                        <#if level1Menu.folderMenu>
                            <li class="menu">
                                <a href="###"><@StI18n level1Menu.menuCode/></a>
                                <ul class="sub">
                                    <#list level1Menu.subMenu as level2Menu>
                                        <li><a href="${level2Menu.menuLink}"><@StI18n level2Menu.menuCode/></a></li>
                                    </#list>
                                </ul>
                                <span></span>
                            </li>
                        <#else>
                            <li><a href="${level1Menu.menuLink}"><@StI18n level1Menu.menuCode/></a></li>
                        </#if>
                    </#list>
                </@StMenu>
            </ul>
        </nav>
        <h2 id="mnavh"><span class="navicon"></span></h2>
    </div>
    <div class="is-search">
        <section><i></i>请输入关键字词</section>
    </div>
    <div class="search-page">
        <div class="go-left"></div>
        <div class="search">
            <form action="/search" method="get" name="searchform" id="searchform">
                <input name="keyboard" id="keyboard" class="input_text" value="请输入关键字词1"
                       style="color: rgb(153, 153, 153);"
                       onfocus="if(value==='请输入关键字词1'){this.style.color='#000';value=''}"
                       onblur="if(!value){this.style.color='#999';value='请输入关键字词1'}" type="text">
                <input name="show" value="title" type="hidden">
                <input name="tempid" value="1" type="hidden">
                <input name="tbname" value="news" type="hidden">
                <input name="Submit" class="input_submit" value="搜索" type="submit">
            </form>
        </div>
        <div class="clear"></div>
        <div class="hot-search">
            <p>热门搜索排行</p>
            <ul class="search-paihang">
                <@StKeyword num=10>
                    <#list stHotKeywords as item>
                        <li><a href="/keyword/${item.keyword}"><i>${item_index+1}</i>${item.keyword}</a></li>
                    </#list>
                </@StKeyword>
            </ul>
        </div>
<#--        <div class="new-search">-->
<#--            <p>最新搜索排行</p>-->
<#--            <ul class="search-paihang">-->
<#--                <li><a href=""><i>1</i>函数</a></li>-->
<#--                <li><a href="/"><i>2</i>博客程序</a></li>-->
<#--                <li><a href="/"><i>3</i>解析</a></li>-->
<#--                <li><a href="/"><i>4</i>红色</a></li>-->
<#--                <li><a href="/"><i>5</i>告别2018</a></li>-->
<#--                <li><a href="/"><i>6</i>插件</a></li>-->
<#--                <li><a href="/"><i>7</i>网页布局</a></li>-->
<#--                <li><a href="/"><i>8</i>云服务器</a></li>-->
<#--                <li><a href="/"><i>9</i>with love for you</a></li>-->
<#--                <li><a href="/"><i>10</i>评论插件</a></li>-->
<#--            </ul>-->
<#--        </div>-->
    </div>
</header>
<div class="clear blank"></div>
<article>
    <#nested />
    <#if aside>
    <aside class="side-section right-box">
        <@StAside>
            <#list stWidgetList as asideItem>
                <#switch asideItem.widgetCode>
                    <#case "widget-hot">
                    <div class="whitebg tuijian">
                        <h2 class="side-title">${asideItem.widgetName}</h2>
                        <@StArticles type="hot" size=6>
                            <#list stArticleList as item>
                                <#if item_index==1>
                                    <ul>
                                </#if>

                                <#if item_index==0>
                                    <section class="topnews imgscale">
                                        <a href="/article/content/${item.alias}">
                                            <img src="/static/images/article/0.jpg" alt="${item.title}">
                                            <span>${item.title}</span>
                                        </a>
                                    </section>
                                <#else>
                                    <li>
                                        <a href="/article/content/${item.alias}" title="${item.title}" target="_blank">
                                            <i><img src="/static/images/article/${item_index%10}.jpg"
                                                    alt="${item.title}"></i>
                                            <p>${item.title}</p>
                                        </a>
                                    </li>
                                </#if>

                                <#if !item_has_next>
                                    </ul>
                                </#if>
                            </#list>
                        </@StArticles>
                    </div>
                    <#break>

                    <#case "widget-new">
                    <div class="whitebg tuijian">
                        <h2 class="side-title">${asideItem.widgetName}</h2>
                        <@StArticles type="last" size=6>
                            <#list stArticleList as item>
                                <#if item_index==1>
                                    <ul>
                                </#if>

                                <#if item_index==0>
                                    <section class="topnews imgscale">
                                        <a href="/article/content/${item.alias}">
                                            <img src="/static/images/article/0.jpg" alt="${item.title}">
                                            <span>${item.title}</span>
                                        </a>
                                    </section>
                                <#else>
                                    <li>
                                        <a href="/article/content/${item.alias}" title="${item.title}" target="_blank">
                                            <i><img src="/static/images/article/${item_index%10}.jpg"
                                                    alt="${item.title}"></i>
                                            <p>${item.title}</p>
                                        </a>
                                    </li>
                                </#if>

                                <#if !item_has_next>
                                    </ul>
                                </#if>
                            </#list>
                        </@StArticles>
                    </div>
                    <#break>

                    <#case "widget-random">
                    <div class="whitebg suiji">
                        <h2 class="side-title">${asideItem.widgetName}</h2>
                        <@StArticles type="random" size=10>
                            <ul>
                            <#list stArticleList as item>
                                <li><a href="/article/content/${item.alias}" title="${item.title}" target="_blank">${item.title}</a></li>
                            </#list>
                            </ul>
                        </@StArticles>
                    </div>
                    <#break>

                    <#case "widget-view">
                    <div class="whitebg paihang">
                        <h2 class="side-title">${asideItem.widgetName}</h2>
                        <@StArticles type="view" size=11>
                            <#list stArticleList as item>
                                <#if item_index==1>
                                    <ul>
                                </#if>

                                <#if item_index==0>
                                    <section class="topnews imgscale">
                                        <a href="/article/content/${item.alias}">
                                            <img src="/static/images/article/0.jpg" alt="${item.title}">
                                            <span>${item.title}</span>
                                        </a>
                                    </section>
                                <#else>
                                    <li>
                                        <i></i>
                                        <a href="/article/content/${item.alias}" title="${item.title}"
                                           target="_blank">${item.title}</a>
                                    </li>
                                </#if>

                                <#if !item_has_next>
                                    </ul>
                                </#if>
                            </#list>
                        </@StArticles>
                    </div>
                    <#break>

                    <#case "widget-tags">
                    <div class="whitebg cloud">
                        <h2 class="side-title">${asideItem.widgetName}</h2>
                        <ul>
                            <@StKeyword num=30>
                                <#list stHotKeywords as item>
                                    <a href="/keyword/${item.keyword}" target="_blank">${item.keyword}</a>
                                </#list>
                            </@StKeyword>
                        </ul>
                    </div>
                    <#break>

                    <#case "widget-adv">
                    <div class="advimg advimg-aside advimg-small">小图广告（320*140）</div>
                    <#break>
                </#switch>
                <#if asideItem_has_next>
                    <div class="blank clear"></div>
                </#if>
            </#list>
        </@StAside>
    </aside>
    </#if>
</article>
<div class="clear blank"></div>
<footer>
    <div class="footer box">
        <div class="wxbox">
            <ul>
                <li><img src="<@StConfig key="st_wx_shop_qr" />"><span>微信小店</span></li>
                <li><img src="<@StConfig key="st_wx_qr" />"><span>微信公众号</span></li>
            </ul>
        </div>
        <div class="endnav">
            <p><b>站点声明：</b></p>
            <p>1、本站内容来源于互联网，著作权归属原作者或团队。</p>
            <p>2、本站内容若侵犯您的权益，请告知，站长会立即处理。</p>
            <p>3、本站内容未经许可，不得用于任何商业目的。</p>
        </div>
        <div class="endnav" style="text-align: center;clear: both;">
            <p>Copyright © All Rights Reserved. 备案号：<a href="http://www.miitbeian.gov.cn/" target="_blank"
                                                       rel="nofollow"><@StConfig key="st_icp" default="未设置"/></a> <a href="" target="_blank">网站地图</a>
            </p>
        </div>
    </div>
</footer>
<div class="toolbar-open"></div>
<div class="toolbar">
    <div class="toolbar-close"><span id="closed"></span></div>
    <div class="toolbar-nav">
        <ul id="toolbar-menu">
            <li><i class="side-icon-user"></i>
                <section>
                    <div class="userinfo">
                        <form name=login method=post action="[!--news.url--]e/member/doaction.php">
                            <input type=hidden name=enews value=login>
                            <input type=hidden name=ecmsfrom value=9>
                            <input name="username" type="text" class="inputText" size="16" placeholder="用户名"/>
                            <input name="password" type="password" class="inputText" size="16" placeholder="密码"/>
                            <input type="submit" name="Submit" value="登陆" class="inputsub-dl"/>
                            <a href="[!--news.url--]e/member/register/index.php?tobind=0&groupid=1"
                               class="inputsub-zc">注册</a>
                        </form>
                        <!--登陆后状态-->
                        <!--
                        <div class="clear"></div>
                            <div class="logged">
                            <b>XXX管理员，您好！</b> <a href="[!--news.url--]e/member/cp/" target="_parent">会员中心</a> <a href="[!--news.url--]e/member/doaction.php?enews=exit&ecmsfrom=9" onclick="return confirm('确认要退出?');">退出</a>
                        </div>
                          -->
                    </div>
                </section>
            </li>
            <li><i class="side-icon-qq"></i>
                <section class="qq-section">
                    <div class="qqinfo"><a href="#">前端设计交流群①</a><a href="#">前端设计交流群②</a><a
                                href="http://wpa.qq.com/msgrd?v=3&uin=9490489&site=qq&menu=yes">站长QQ</a></div>
                </section>
            </li>
            <li><i class="side-icon-weixin"></i>
                <section class="weixin-section">
                    <div class="weixin-info">
                        <p>个人微信扫码</p>
                        <img src="/static/images/replace/wx.png">
                        <p class="text12">工作时间</p>
                        <p class="text12">周一至周日 9:00-21:00</p>
                    </div>
                </section>
            </li>
            <li><i class="side-icon-dashang"></i>
                <section class="dashang-section">
                    <p>如果你觉得本站很棒，可以通过扫码支付打赏哦！</p>
                    <ul>
                        <li><img src="/static/images/replace/weipayimg.jpg">微信收款码</li>
                        <li><img src="/static/images/replace/alipayimg.jpg">支付宝收款码</li>
                    </ul>
                </section>
            </li>
        </ul>
    </div>
</div>
<div class="endmenu">
    <ul>
        <@StMenu group="footer">
            <#list stMenuGroup as level1Menu>
                <li><a href="${level1Menu.menuLink}"><i class="iconfont ${level1Menu.menuIcon}"></i><@StI18n level1Menu.menuCode/></a></li>
            </#list>
        </@StMenu>
    </ul>
</div>
<a href="#" title="返回顶部" class="icon-top"></a>
</body>
</html>
</#macro>

<#macro PagePager result url="">
    <div class="pagelist">
        <#--第一页，禁用“上一页”按钮-->
        <#if result.totalNum == 0 || result.pageNum == 1>
            <a href="###" class="disabled">上一页</a>
        <#else>
            <a href="${url}${result.pageNum-1}">上一页</a>
        </#if>

        <#--第一页-->
        <#if (result.totalNum > 0)>
            <#if result.pageNum == 1>
                <b>1</b>
            <#else>
                <a href="${url}1">1</a>
            </#if>
        </#if>

        <#--如果不只有一页-->
        <#if (result.totalPages > 1)>
        <#--如果当前页往前查3页不是第2页-->
            <#if ((result.pageNum - 3) > 2)>
                <a href="###" class="disabled">...</a>
            </#if>

        <#--当前页的前3页和后3页-->
            <#list (result.pageNum - 3)..(result.pageNum + 3) as index>
            <#--如果位于第一页和最后一页之间-->
                <#if (index > 1) && (index < result.totalPages)>
                    <#if result.pageNum == index>
                        <b>${index}</b>
                    <#else>
                        <a href="${url}${index}">${index}</a>
                    </#if>
                </#if>
            </#list>

        <#--如果当前页往后查3页不是倒数第2页-->
            <#if (result.pageNum + 3) < (result.totalPages - 1)>
                <a href="###" class="disabled">...</a>
            </#if>

        <#--最后页-->
            <#if result.pageNum == result.totalPages>
                <b>${result.totalPages}</b>
            <#else>
                <a href="${url}${result.totalPages}">${result.totalPages}</a>
            </#if>
        </#if>

        <#--最后页，禁用“下一页”按钮-->
        <#if result.totalNum == 0 || result.pageNum == result.totalPages>
            <a href="###" class="disabled">下一页</a>
        <#else>
            <a href="${url}${result.pageNum+1}">下一页</a>
        </#if>
    </div>
</#macro>