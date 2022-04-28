#version 420

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;

out vec3 color;
//out vec3 pos;

uniform mat4 projection;

void main() {
    color = vertex_color;
//    pos = vertex_position;
    gl_Position = projection * vec4(vertex_position, 1);
}