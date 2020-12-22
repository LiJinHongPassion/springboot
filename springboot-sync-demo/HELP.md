## `springboot`异步任务（有返回值的）

>本`demo`的业务是：`Controller.test()`方法调用`SyncService.testSync()`方法，在该方法中异步调用`jobService.hasReturnValue()`,同时`SyncService.testSync()`继续执行本身的业务逻辑，在`testSync`方法最后通过自旋的方式来判断异步调用是否完成，自旋方法里面有个阈值，自旋达到阈值后就退出阻塞状态

## 拓展
[伪命题 - 多线程事务](https://segmentfault.com/a/1190000037770701)