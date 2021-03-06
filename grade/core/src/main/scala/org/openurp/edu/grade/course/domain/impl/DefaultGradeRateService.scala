package org.openurp.edu.grade.course.domain.impl

import java.text.NumberFormat
import java.{ util => ju }

import org.beangle.commons.lang.{ Numbers, Strings }
import org.beangle.commons.script.ExpressionEvaluator
import org.beangle.data.dao.{ EntityDao, OqlBuilder }
import org.openurp.edu.base.code.model.ScoreMarkStyle
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.service.ProjectCodeService
import org.openurp.edu.grade.code.model.GradeType
import org.openurp.edu.grade.course.domain.GradeRateService
import org.openurp.edu.grade.model.{ Grade, GradeRateConfig }

class DefaultGradeRateService extends GradeRateService {

  var entityDao: EntityDao = _

  var expressionEvaluator: ExpressionEvaluator = _

  var projectCodeService: ProjectCodeService = _
  /**
   * 依照绩点规则计算平均绩点
   *
   * @param rule
   */
  def calcGp(grade: Grade, gradeType: GradeType): java.lang.Float = {
    val conifg = getConfig(grade.std.project, grade.markStyle)
    if (null != conifg) {
      var gp = calcGp(grade.score, conifg)
      if (null != gp && gp.floatValue > 1 && null != grade.score && grade.score < 61) {
        if (grade.gradeType.id == GradeType.MakeupGa) gp = 1.0f
      }
      return gp
    }
    null
  }

  /**
   * 计算分数对应的绩点
   *
   * @param score
   * @param conifg
   * @return
   */
  private def calcGp(score: java.lang.Float, conifg: GradeRateConfig): java.lang.Float = {
    if (null == score || score.floatValue() <= 0) return new java.lang.Float(0) else {
      var iter = conifg.items.iterator
      while (iter.hasNext) {
        val gradeRateItem = iter.next()
        if (gradeRateItem.inScope(score)) {
          if (Strings.isNotEmpty(gradeRateItem.gpExp)) {
            val data = new ju.HashMap[String, Any]
            data.put("score", score)
            return expressionEvaluator.eval(gradeRateItem.gpExp, data, classOf[Float])
          } else {
            return null
          }
        }
      }
    }
    new java.lang.Float(0)
  }

  /**
   * 将字符串按照成绩记录方式转换成数字.<br>
   * 空成绩将转换成null
   *
   * @param score
   * @param markStyle
   * @return
   */
  def convert(score: String, scoreMarkStyle: ScoreMarkStyle, project: Project): java.lang.Float = {
    if (Strings.isBlank(score)) return null
    val config = getConfig(project, scoreMarkStyle).asInstanceOf[GradeRateConfig]
    if (null == config || config.items.size == 0) {
      if (Numbers.isDigits(score)) new java.lang.Float(Numbers.toFloat(score)) else null
    } else {
      val newScore = config.convert(score)
      if (null != newScore) {
        return newScore
      }
      if (Numbers.isDigits(score)) {
        return new java.lang.Float(Numbers.toFloat(score))
      }
      null
    }
  }

  def isPassed(score: java.lang.Float, scoreMarkStyle: ScoreMarkStyle, project: Project): Boolean = {
    val config = getConfig(project, scoreMarkStyle)
    if (null == config || null == score) {
      false
    } else {
      java.lang.Float.compare(score, config.passScore) >= 0
    }
  }

  /**
   * 将字符串按照成绩记录方式转换成数字.<br>
   * 空成绩将转换成""
   *
   * @param score
   * @param markStyle
   * @return
   */
  def convert(score: java.lang.Float, scoreMarkStyle: ScoreMarkStyle, project: Project): String = {
    if (null == score) {
      return ""
    }
    val config = getConfig(project, scoreMarkStyle)
    if (null == config) {
      NumberFormat.getInstance.format(score.floatValue())
    } else {
      config.convert(score)
    }
  }

  /**
   * 查询记录方式对应的配置
   */
  def getConfig(project: Project, scoreMarkStyle: ScoreMarkStyle): GradeRateConfig = {
    if (null == project || !project.persisted) return null
    val builder = OqlBuilder.from(classOf[GradeRateConfig], "config")
      .where("config.project=:project and config.scoreMarkStyle=:markStyle", project, scoreMarkStyle)
      .cacheable()
    val rs = entityDao.search(builder)
    if (rs.isEmpty) null else rs.head
  }

  /**
   * 获得支持的记录方式
   *
   * @param project
   * @return
   */
  def getMarkStyles(project: Project): Seq[ScoreMarkStyle] = {
    val builder = OqlBuilder.from(classOf[GradeRateConfig], "config")
      .where("config.project=:project", project)
      .cacheable()
    val rs = entityDao.search(builder)
    if (rs.isEmpty) {
      projectCodeService.getCodes(project, classOf[ScoreMarkStyle])
    } else {
      rs.map(_.markStyle)
    }
  }
}
