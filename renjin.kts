@file:MavenRepository("renjin", "https://nexus.bedatadriven.com/content/groups/public")
@file:DependsOn("org.renjin:renjin-script-engine:0.9.2666")

import javax.script.*
import org.renjin.script.*

val factory = RenjinScriptEngineFactory()
val engine = factory.scriptEngine
engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))")
engine.eval("print(df)")
engine.eval("print(lm(y ~ x, df))")

