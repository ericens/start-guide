-- MAT 分析工具，有语法提示。
-- select 查询什么  from 从哪里查  where  过滤条件。
1. 查什么: 对象，对象的属性，对象inbounds, 对象outbounds, 对象浅堆深堆大小等等
2. 从哪里查： 类全程，正则表达式的类全程，类的地址。
3. 过滤条件：大小、是否为空，字符串表示的like

-- 查看有哪些类
SELECT * FROM "geym\.zbase\..*"

SELECT  objects v FROM "geym\.zbase\.ch7\.heap\..*" v  where v.@retainedHeapSize > 3000

SELECT  objects inbounds(v) FROM "geym\.zbase\.ch7\.heap\..*" v WHERE (v.@retainedHeapSize > 3000)
SELECT  objects outbounds(v) FROM "geym\.zbase\.ch7\.heap\..*" v WHERE (v.@retainedHeapSize > 3000)

SELECT v.@objectId, v.@displayName FROM "geym\.zbase\.ch7\.heap\..*" v WHERE (v.@retainedHeapSize > 3000)


-- 查看文件
SELECT toString(f.path.value) FROM java.io.File f

SELECT objects v FROM java.lang.String v WHERE (v != null )

-- 过滤长度
SELECT objects v FROM java.lang.String v WHERE (v != null and v.value.@length > 30 )

SELECT objects v FROM java.lang.String v WHERE (v != null and toString(v.value) like  "http.*" )
SELECT objects v FROM java.lang.String v WHERE (v != null and toString(v) like  "http.*" )


