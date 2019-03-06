data class Process(val id: Int, val inputs: List<String>, val outputs: List<String>)
typealias Pipeline = List<Process>
typealias Branch = Pair<Process, Pipeline>

val processes = listOf(
    Process(1, listOf("A"), listOf("B")),
    Process(2, listOf("B"), listOf("C", "D")),
    Process (3, listOf("A", "F"), listOf("E")),
    Process(4, listOf("E"), listOf("F"))
)

fun pipelinesFromDataset(dataset: String): List<Pipeline> {
  val childProcesses = processForInputDataset(dataset)
  val branches: List<Branch> = childProcesses.map { Pair(it, listOf<Process>()) }
  return calculatePipelines(branches, listOf())
}

fun processForInputDataset(dataset: String): List<Process> =
  processes.filter { it.inputs.contains(dataset) }

fun connectedProcesses(process: Process): List<Process> =
  process.outputs.flatMap { processForInputDataset(it) }

tailrec fun calculatePipelines(exploring: List<Branch>, explored: List<Pipeline>): List<Pipeline> {
  if(exploring.isEmpty()) return explored

  // Extract branch to explore
  val head = exploring.first()
  val tail = exploring.drop(1)

  // Prepare to explore
  val actualProcess: Process = head.first
  val actualPipeline: Pipeline = head.second
  val childProcesses =
      connectedProcesses(actualProcess)
      .filter { !actualPipeline.contains(it) }

  return if(childProcesses.isEmpty()) {
    // We finished the exploration of this pipeline
    val newPipeline: Pipeline = actualPipeline + listOf(actualProcess)
    val newExplored: List<Pipeline> = explored + listOf(newPipeline)
    calculatePipelines(tail, newExplored)
  } else {
    // We still have children to explore
    val branches: List<Branch> =
        childProcesses.map { Pair(it, actualPipeline + listOf(actualProcess)) }
    calculatePipelines(branches + tail, explored)
  }
}

fun printPipelines(pipelines: List<Pipeline>) {
  pipelines.forEach{ pipeline ->
    println("\nPipeline:")
    println("----------------------------")
    pipeline.forEach { process ->
      println("  * $process")
    }
    println()
  }
}

fun graphPipelines(pipelines: List<Pipeline>): String {
  val graph =
    pipelines.flatMap { pipeline ->
      pipeline.flatMap { process ->
        process.inputs.map { input ->
          "\"$input\" [shape=box]\n" +
          "\"$input\" -> \"${process.id}\""
        } +
        process.outputs.map { output ->
          "\"$output\" [shape=box]\n" +
          "\"${process.id}\" -> \"$output\""
        }
      }
    }
  return """
  digraph Pipeline           {
    label = "Sample_Pipeline" ;

    ${graph.joinToString("\n")}
  }
  """
}

val res = pipelinesFromDataset("A")
println(graphPipelines(res))
