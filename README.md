# doct
a document (xls, ...) template engine 


命令格式：
{%id:命令 参数 ...%}   id: 可以省略
特性：
1. 支持OGNL表达式
2. 支持FOR循环

命令列表：
{%set aa 10%}	#设置属性值

{%get aa%}    	#获取属性值

{%offset 1 1%}	#行列偏移

{%offset_add 0 1%}	#行列偏移

{%insert_row%}

{%insert_cel%}

{%for aa in list%}	#for循环

{%endfor%}		#for循环结束标签

{%end%}			#本行结束，之后的命令不在解析
