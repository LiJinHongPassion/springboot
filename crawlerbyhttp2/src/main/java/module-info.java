//jdk9的模块化,需要该文件,否则无法正常运行
module crawlerbyhttp2{
    requires jdk.incubator.httpclient;
    requires slf4j.api;
    requires tomcat.coyote;
    requires http.request;
}
