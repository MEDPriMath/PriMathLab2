#version 420

layout(location = 0) in vec3 vertex_position;

out vec3 pos;
out vec3 view_pos;

uniform mat4 projection;

/* proj_to_pos */

void main() {
    pos = vertex_position;
    view_pos = proj_to_pos(projection);
    gl_Position = projection * vec4(vertex_position, 1);
}