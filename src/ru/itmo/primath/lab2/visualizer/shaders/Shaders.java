package ru.itmo.primath.lab2.visualizer.shaders;

import java.util.List;
import java.util.Map;

import static ru.itmo.primath.lab2.util.IOUtils.readResourceFile;

public class Shaders {

    // Default shader
    public static Shader defaultShader;

    // Mesh shaders
    public static List<MeshShader> meshShaders;

    private static Map<String, String> functionShaders;

    public static void init() {
        functionShaders = Map.of(
                "fog", readResourceFile("shaders/functions/fog.glsl"),
                "proj_to_pos", readResourceFile("shaders/functions/proj_to_pos.glsl"),
                "levels", readResourceFile("shaders/functions/levels.glsl")
        );

        defaultShader = new DefaultShader(
                readShaderCode("shaders/vert_default.glsl"),
                readShaderCode("shaders/frag_default.glsl"));

        meshShaders = List.of(
                new WhiteCellsShader(
                        readShaderCode("shaders/mesh_vert.glsl"),
                        readShaderCode("shaders/mesh_frag_white_cell.glsl")),
                new ColorfulShader(
                        readShaderCode("shaders/mesh_vert.glsl"),
                        readShaderCode("shaders/mesh_frag_colorful.glsl")),
                new MinMaxColorfulShader(
                        readShaderCode("shaders/mesh_vert_min_max.glsl"),
                        readShaderCode("shaders/mesh_frag_min_max_colorful.glsl"))
        );
    }

    private static String readShaderCode(String resourceFileName) {
        String code = readResourceFile(resourceFileName);
        for (Map.Entry<String, String> entry : functionShaders.entrySet()) {
            String name = entry.getKey();
            String function = entry.getValue();
            code = code.replace(String.format("/* %s */", name), function);
        }
        return code;
    }
}
