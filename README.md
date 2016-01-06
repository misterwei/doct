# doct
a document (xls, ...) template engine 

变量形式：
\#context
user.name

命令格式：
命令关键字 + 空格 + 参数
每一个命令用分号结束

命令执行顺序：先横向再纵向
{%  %} 命令行
get aaa; 命令
{%id_get:get aaa;get bbb;get ccc;%}

命令列表：
offset #row #cell 设置行列偏移
get user.name  获取值，并输出在单元格内
set #row 1 设置变量的值

IF命令：
if 条件  
elif 条件
else 
endif
例子：
if #user_index ==0;style yellow 5;else;style red 5;endif;


FOR命令：
for item in list  item是接受list里的循环变量，索引值为 #item_index
break   跳出循环
continue  进入下一次循环
endfor


offset命令：
offset rowoffset celloffset 设置行列偏移
offset_add rowoffset [celloffset] 在原有偏移基础上+
最后用完offset命令后需要置零
offset 0 0

offset_save命令：
offset_save [rowoffset [celloffset]] 存储当前的偏移
offset_restore命令：
offset_restore 恢复之前存储的偏移

insert_row命令：
insert_row [rows] 插入行，rows行数

height 命令：
height 680  设置行高

width 命令：
width 1100 设置列宽


style命令：
style [row cell cells] 
style [id [cells]]  设置样式

copy命令：
copy row cell count 
copy id count   复制单元格

remove命令：
remove row cell cells 从 row，cell开始移除几个
remove id cells 从ID开始移除几个
remove cells 当前开始移除几个
remove    删除当前单元格


merge命令：
merge rows cells 向右下方合并多少

unmerge命令：
unmerge id   取消合并id所在的单元格

