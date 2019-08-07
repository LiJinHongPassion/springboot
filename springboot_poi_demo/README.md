最简单的poi操作
- 导出（可导出本地图片/网络图片 + 内容）
- 导入（例子中给出的是将图片能够上传至fastdfs的思路，如果不需要上传至fastdfs，则将MyExcelImportService.java删除，并将ExcelUtils中的new MyExcelImportService()，更换为new ExcelImportService()）

简单讲一下导入，easypoi只有两种保存图片方式：一种保存到服务器目录下，一种保存到数据库，这也对应了@Excel里面的注释描述；
如需要将文件上传至文件服务器？
解决方法：自己重写ExcelImportService.java(实际上是重写他的saveImage里面保存图片的方法)。
然后直接使用这个自定义的ExcelImportService进行导入，不能在使用之前easypoi提供的导入入口。