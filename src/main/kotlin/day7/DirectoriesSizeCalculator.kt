package day7

private const val ROOT = "/"


object DirectoriesSizeCalculator {

    private var dirs: MutableMap<String, Long> = mutableMapOf()
    private var currentPath: String = ROOT

    fun getDirsSizes(lines: List<String>): Map<String, Long> {
        dirs = mutableMapOf()
        currentPath = ROOT
        for (line in lines) {
            when {
                line.startsWith("$ ") -> processCommand(line)
                line.matches("^\\d+.*".toRegex()) -> processNewFile(line)
                line.startsWith("dir ") -> continue
                else -> error("Unexpected line")
            }
        }
        return dirs

    }

    private fun processCommand(line: String) {
        when (line.split(" ")[1]) {
            "cd" -> processChangeDir(line.split(" ")[2])
            "ls" -> return
        }
    }

    private fun processChangeDir(arg: String) {
        when (arg) {
            ROOT -> currentPath = ROOT
            ".." -> currentPath = getOuterDirOrDefault(currentPath, ROOT)
            else -> currentPath += "$arg/"
        }
    }

    private fun processNewFile(line: String) {
        val size = line.split(" ")[0].toInt()
        var path = currentPath

        while (path.isNotBlank()) {
            dirs[path] = dirs.getOrDefault(path, 0L) + size
            path = getOuterDirOrDefault(path, "")
        }
    }

    private fun getOuterDirOrDefault(path: String, default: String): String {
        if (path == ROOT)
            return default
        return path.dropLast(1).dropLastWhile { it != '/' }
    }

}



