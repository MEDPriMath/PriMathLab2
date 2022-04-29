#version 420

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;

out vec3 color;
out vec3 pos;
//out float out_min;
//out float out_max;

uniform mat4 projection;
//uniform float min;
//uniform float max;

void main() {
    color = vertex_color;
    pos = vertex_position;
//    out_min = min;
//    out_max = max;
    gl_Position = projection * vec4(vertex_position, 1);
}