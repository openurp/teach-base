package org.openurp.teach.exam.model

import org.beangle.data.model.bean.NamedBean
import org.openurp.base.Semester
import org.openurp.teach.code.ExamType
import org.openurp.teach.core.ProjectBasedObject
import org.openurp.teach.exam.{ExamActivity, ExamBatch, ExamBatchLesson, ExamBatchTime, ExamTake}

class ExamBatchBean extends ProjectBasedObject[Integer] with ExamBatch with NamedBean{

  /** 排考批次学期 */
  var semester: Semester = _

  /** 批次时间开关 */
  var batchTime: ExamBatchTime = _

  /** 默认考试类型 */
  var defaultExamType: ExamType = _

  /** 批次任务范围 */
  var lessons: collection.mutable.Set[ExamBatchLesson] = _

  /** 批次考试名单 */
  var examTakes: collection.mutable.Set[ExamTake] = _

  var activities: collection.mutable.Set[ExamActivity] = _

  /** 是否归档 */
  var archived: Boolean = false

}