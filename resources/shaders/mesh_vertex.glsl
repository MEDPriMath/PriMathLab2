#version 420

layout(location = 0) in vec3 vertex_position;

out vec3 pos;

uniform mat4 projection;

void main() {
    pos = vertex_position;
    gl_Position = projection * vec4(vertex_position, 1);
}