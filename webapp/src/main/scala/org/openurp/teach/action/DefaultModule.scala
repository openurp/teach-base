package org.openurp.teach.action

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.teach.action.code.DegreeAction
import org.openurp.teach.action.code.DisciplineAction
import org.openurp.teach.action.code.DisciplineCatalogAction
import org.openurp.teach.action.code.StdLabelAction
import org.openurp.teach.action.code.StdLabelTypeAction
import org.openurp.teach.action.code.StdStatusAction
import org.openurp.teach.action.code.StdTypeAction
import org.openurp.teach.action.code.StudyTypeAction
import org.openurp.teach.action.code.TeacherStateAction
import org.openurp.teach.action.code.TeacherTitleAction
import org.openurp.teach.action.code.TeacherTitleLevelAction
import org.openurp.teach.action.code.TeacherTypeAction
import org.openurp.teach.action.code.TeacherUnitTypeAction
import org.openurp.teach.action.code.TutorTypeAction
import org.openurp.teach.action.ProjectConfigAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[TeacherTypeAction],classOf[TeacherStateAction],classOf[TeacherUnitTypeAction],classOf[TutorTypeAction],classOf[StdStatusAction])
    bind(classOf[DisciplineAction],classOf[DisciplineCatalogAction])
    bind(classOf[TeacherTitleAction],classOf[TeacherTitleLevelAction],classOf[StudyTypeAction],classOf[DegreeAction])
    bind(classOf[StdLabelAction],classOf[StdLabelTypeAction],classOf[StdTypeAction])
    bind(classOf[AdminclassAction],classOf[MajorAction],classOf[DirectionAction]
    		,classOf[DirectionJournalAction],classOf[ProjectAction],classOf[ProjectCodeAction],classOf[MajorJournalAction])
    bind(classOf[ProjectClassroomAction],classOf[ProjectConfigAction],classOf[ProjectPropertyAction])
  }
}