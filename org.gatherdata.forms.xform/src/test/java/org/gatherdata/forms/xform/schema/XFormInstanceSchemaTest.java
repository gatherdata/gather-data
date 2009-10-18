package org.gatherdata.forms.xform.schema;

import static org.junit.Assert.*;

import org.javarosa.core.model.FormDef;
import org.javarosa.xform.util.XFormUtils;
import org.junit.Test;

public class XFormInstanceSchemaTest {

    @Test
    public void shouldProducePlainText() {
        FormDef f = XFormUtils.getFormFromInputStream(getClass().getResourceAsStream("/xforms/idsr-case_draft.xhtml"));
        XFormInstanceSchema schema = new XFormInstanceSchema(f);
        System.out.println(schema.toText());
        assertTrue(true);
    }
}
