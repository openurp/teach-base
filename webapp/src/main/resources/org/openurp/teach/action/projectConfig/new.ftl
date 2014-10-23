[#ftl]
[@b.head/]
[@b.toolbar title="新建项目配置"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]

    [@b.select name="projectConfig.project.id" label="项目名称" value="${(projectConfig.project.name)!}" required="true" 
               style="width:200px;" items=projects option="id,name" empty="..."/]
    [@b.select name="projectConfig.meta.id" label="代码元" value="${(projectConfig.meta.name)!}" required="true" 
               style="width:200px;" items=metas option="id,name" empty="..."/]
    [@b.textfield name="projectConfig.codeId" label="代码ID" value="${projectConfig.codeId!}" required="true" maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]


